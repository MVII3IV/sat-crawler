package com.mvii3iv.sat.crawler.components.employee;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeesRepository extends MongoRepository<Employees, String> {
    public List<Employees> findByBossRfc(String bossRfc);
}
