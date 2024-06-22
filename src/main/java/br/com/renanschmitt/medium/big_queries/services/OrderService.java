package br.com.renanschmitt.medium.big_queries.services;

import br.com.renanschmitt.medium.big_queries.model.Customer;
import br.com.renanschmitt.medium.big_queries.model.Order;
import br.com.renanschmitt.medium.big_queries.model.OrderItem;
import br.com.renanschmitt.medium.big_queries.model.Product;
import br.com.renanschmitt.medium.big_queries.repositories.CustomerRepository;
import br.com.renanschmitt.medium.big_queries.repositories.OrderRepository;
import br.com.renanschmitt.medium.big_queries.repositories.ProductRepository;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService {

  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public void create(Integer numberOfOrders) {
    var faker = new Faker();

    var customers = new ArrayList<Customer>();
    customerRepository.findAll().forEach(customers::add);

    var products = new ArrayList<Product>();
    productRepository.findAll().forEach(products::add);

    var sellOrders =
        IntStream.range(0, numberOfOrders)
            .parallel()
            .mapToObj(
                i -> {
                  var sellOrder = new Order();
                  sellOrder.setDate(
                      faker
                          .date()
                          .past(1000, TimeUnit.DAYS)
                          .toInstant()
                          .atZone(ZoneId.systemDefault())
                          .toLocalDate());
                  sellOrder.setCustomer(faker.options().nextElement(customers));
                  sellOrder.setStatus(
                      faker
                          .options()
                          .nextElement(
                              List.of(
                                  "Pending", "Processing", "Shipped", "Delivered", "Canceled")));
                  sellOrder.setPaymentMethod(
                      faker
                          .options()
                          .nextElement(List.of("Credit Card", "PayPal", "Bank Transfer")));
                  sellOrder.setShippingCost(
                      BigDecimal.valueOf(faker.random().nextInt(100, 500), 2));
                  sellOrder.setDiscountAmount(
                      BigDecimal.valueOf(faker.random().nextInt(100, 500), 2));
                  sellOrder.setCurrency(faker.currency().code());
                  sellOrder.setTrackingNumber(faker.random().nextInt(1000000));

                  var numberOfItems = faker.random().nextInt(1, 10);

                  var sellOrderTotalAmount = BigDecimal.ZERO;

                  for (int j = 0; j < numberOfItems; j++) {
                    var orderItem = new OrderItem();
                    orderItem.setProduct(faker.options().nextElement(products));
                    orderItem.setQuantity(faker.random().nextInt(1, 100));
                    orderItem.setPrice(BigDecimal.valueOf(faker.random().nextInt(100, 50000), 2));
                    orderItem.setTotalAmount(
                        orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
                    orderItem.setDiscount(BigDecimal.valueOf(faker.random().nextInt(100, 500), 2));
                    orderItem.setTaxAmount(
                        orderItem.getPrice().divide(BigDecimal.TEN, RoundingMode.CEILING));
                    orderItem.setTaxPercent(BigDecimal.TEN);
                    orderItem.setOrder(sellOrder);

                    sellOrderTotalAmount = sellOrderTotalAmount.add(orderItem.getTotalAmount());
                    sellOrder.getOrderItems().add(orderItem);
                  }

                  sellOrder.setTotalAmount(sellOrderTotalAmount);
                  sellOrder.setTaxAmount(
                      sellOrder.getTotalAmount().divide(BigDecimal.TEN, RoundingMode.CEILING));

                  return sellOrder;
                })
            .toList();

    orderRepository.saveAll(sellOrders);
  }
}
