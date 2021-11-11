package com.nielsen.sports.test.scheduler.repository;

import com.nielsen.sports.test.scheduler.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByName(String name);
}
