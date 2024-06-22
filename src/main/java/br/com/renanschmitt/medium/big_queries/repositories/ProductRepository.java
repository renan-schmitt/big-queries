package br.com.renanschmitt.medium.big_queries.repositories;

import br.com.renanschmitt.medium.big_queries.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {}
