package com.mvii3iv.sat.crawler.components.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customers> getCustomers(){
        return customersRepository.findAll();
    }

    public boolean saveCustomer(String rfc, String pass){
        try{
            customersRepository.save(new Customers(rfc, pass));
            return true;
        }catch(Exception e){
            return false;
        }

    }

    public boolean validateCustomer(String rfc, String pass){
        if(!customerExists(rfc)){
            saveCustomer(rfc, pass);
        }
        return customerExists(rfc);
    }

    public boolean customerExists(String rfc){
        Customers customer = customersRepository.findByRfc(rfc);
        if(customer != null)
            return true;
        else
            return false;
    }

}
