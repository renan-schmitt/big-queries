package br.com.renanschmitt.medium.big_queries.api;

import br.com.renanschmitt.medium.big_queries.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Log4j2
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping("/customer/{numberOfCustomers}")
  public ResponseEntity<Object> create(@PathVariable Integer numberOfCustomers) {
    log.info("Creating {} customers", numberOfCustomers);

    customerService.create(numberOfCustomers);

    return ResponseEntity.ok().build();
  }
}
