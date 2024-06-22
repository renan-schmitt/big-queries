package br.com.renanschmitt.medium.big_queries.services;

import br.com.renanschmitt.medium.big_queries.model.Customer;
import br.com.renanschmitt.medium.big_queries.repositories.CustomerRepository;
import com.github.javafaker.Faker;
import java.time.ZoneId;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerService {

  private final CustomerRepository customerRepository;

  public void create(Integer numberOfCustomers) {
    var faker = new Faker();

    var customers =
        IntStream.range(0, numberOfCustomers)
            .parallel()
            .mapToObj(
                i -> {
                  var customer = new Customer();
                  customer.setFirstName(faker.name().firstName());
                  customer.setLastName(faker.name().lastName());
                  customer.setPhone(faker.phoneNumber().phoneNumber());
                  customer.setTitle(faker.name().title());
                  customer.setGender(faker.bool().bool());
                  customer.setBirthDate(
                      faker
                          .date()
                          .birthday()
                          .toInstant()
                          .atZone(ZoneId.systemDefault())
                          .toLocalDate());
                  customer.setStreetAddress(faker.address().streetAddress());
                  customer.setCityAddress(faker.address().city());
                  customer.setState(faker.address().state());
                  customer.setCountry(faker.address().country());
                  customer.setZipCode(faker.address().zipCode());

                  return customer;
                })
            .toList();

    customerRepository.saveAll(customers);
  }
}
