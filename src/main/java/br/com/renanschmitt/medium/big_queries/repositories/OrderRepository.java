package br.com.renanschmitt.medium.big_queries.repositories;

import br.com.renanschmitt.medium.big_queries.model.Order;
import java.util.stream.Stream;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
  Stream<Order> findAllBy();
}
