package com.mvii3iv.sat.crawler.components.employee;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/employees")
public class EmployeesController {

    @Autowired
    EmployeesRepository employeesRepository;

    public EmployeesController(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Employees> getEmployees(@RequestParam String bossRfc){
        return employeesRepository.findByBossRfc(bossRfc);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Employees saveEmployee(@RequestBody Employees employees){
        employeesRepository.save(employees);
        return employees;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Employees updateEmployee(@RequestBody Employees employees){
        employeesRepository.save(employees);
        return employees;
    }

}
