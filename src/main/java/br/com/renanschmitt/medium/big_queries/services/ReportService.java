package br.com.renanschmitt.medium.big_queries.services;

import br.com.renanschmitt.medium.big_queries.dto.ReportDto;
import br.com.renanschmitt.medium.big_queries.model.Order;
import br.com.renanschmitt.medium.big_queries.repositories.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
public class ReportService {
  private final TransactionTemplate transactionTemplate;
  private final OrderRepository orderRepository;
  private final EntityManager entityManager;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public ReportService(
      PlatformTransactionManager platformTransactionManager,
      OrderRepository orderRepository,
      EntityManager entityManager) {
    this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    this.orderRepository = orderRepository;
    this.entityManager = entityManager;

    objectMapper.registerModule(new JavaTimeModule());
  }

  public List<ReportDto> getResult() {
    var result = new ArrayList<ReportDto>();
    for (var order : orderRepository.findAll()) {
      result.add(mapToOrder(order));
    }

    return result;
  }

  @Transactional(readOnly = true)
  public List<ReportDto> getResult2() {
    var result = new ArrayList<ReportDto>();
    try (var orderStream = orderRepository.findAllBy()) {
      orderStream.forEach(
          order -> {
            result.add(mapToOrder(order));
            entityManager.detach(order);
          });
    }

    return result;
  }

  public StreamingResponseBody getResult3() {
    return outputStream ->
        transactionTemplate.execute(
            new TransactionCallbackWithoutResult() {
              @Override
              protected void doInTransactionWithoutResult(TransactionStatus status) {
                fillStream(outputStream);
              }
            });
  }

  private void fillStream(OutputStream outputStream) {
    try (var orderStream = orderRepository.findAllBy()) {
      orderStream.forEach(
          order -> {
            try {
              var json = objectMapper.writeValueAsString(mapToOrder(order));
              outputStream.write(json.getBytes(StandardCharsets.UTF_8));
              entityManager.detach(order);

            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          });
    }
  }

  private ReportDto mapToOrder(Order order) {
    return new ReportDto(
        order.getId(),
        order.getDate(),
        order.getCustomer().getFirstName(),
        order.getTotalAmount(),
        order.getCurrency(),
        order.getStatus(),
        order.getPaymentMethod(),
        order.getOrderItems().stream()
            .map(
                orderItem ->
                    new ReportDto.Item(
                        orderItem.getProduct().getId(),
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getTotalAmount()))
            .toList());
  }
}
