package br.com.renanschmitt.medium.big_queries.api;

import br.com.renanschmitt.medium.big_queries.services.ProductService;
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
public class ProductController {

  private final ProductService productService;

  @PostMapping("/product/{numberOfProducts}")
  public ResponseEntity<Object> create(@PathVariable Integer numberOfProducts) {
    log.info("Creating {} products", numberOfProducts);

    productService.create(numberOfProducts);

    return ResponseEntity.ok().build();
  }
}
