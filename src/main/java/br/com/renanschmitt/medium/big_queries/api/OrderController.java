package br.com.renanschmitt.medium.big_queries.api;

import br.com.renanschmitt.medium.big_queries.services.OrderService;
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
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/sellOrder/{numberOfOrders}")
  public ResponseEntity<Object> create(@PathVariable Integer numberOfOrders) {
    log.info("Creating {} orders", numberOfOrders);

    orderService.create(numberOfOrders);

    return ResponseEntity.ok().build();
  }
}
