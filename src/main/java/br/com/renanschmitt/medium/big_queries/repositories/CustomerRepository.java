package br.com.renanschmitt.medium.big_queries.repositories;

import br.com.renanschmitt.medium.big_queries.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {}
