package br.com.renanschmitt.medium.big_queries.services;

import br.com.renanschmitt.medium.big_queries.model.Product;
import br.com.renanschmitt.medium.big_queries.repositories.ProductRepository;
import com.github.javafaker.Faker;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {

  private final ProductRepository productRepository;

  public void create(Integer numberOfCustomers) {
    var faker = new Faker();

    IntStream.range(0, numberOfCustomers)
        .parallel()
        .forEach(
            i -> {
              var product = new Product();
              product.setName(faker.commerce().productName());
              product.setDescription(faker.lorem().paragraph(5));
              product.setColor(faker.commerce().color());
              product.setMaterial(faker.commerce().material());

              productRepository.save(product);
            });
  }
}
