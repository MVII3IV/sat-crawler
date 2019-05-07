package com.mvii3iv.sat.crawler.components.customers;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomersRepository extends MongoRepository<Customers, String> {
    public Customers findByRfc(String rfc);
}
