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

    /**
     * This method ins in charge of save a Customers object to the data base
     * @param rfc
     * @param pass
     * @return
     */
    public boolean saveCustomer(String rfc, String pass){
        try{
            customersRepository.save(new Customers(rfc, pass));
            return true;
        }catch(Exception e){
            return false;
        }

    }

    /**
     * This method validates if the Customer exists in the database
     * if the user exists return true
     * otherwise the Customer is saved and a true is sent
     * @param rfc
     * @param pass
     * @return
     */
    public boolean validateCustomer(String rfc, String pass){
        if(!customerExists(rfc)){
            saveCustomer(rfc, pass);
        }
        return customerExists(rfc);
    }

    /**
     * This method validates if the Customer exists in the database
     * if the user exists return true
     * otherwise returns false
     * @param rfc
     * @return
     */
    public boolean customerExists(String rfc){
        Customers customer = customersRepository.findByRfc(rfc);
        if(customer != null)
            return true;
        else
            return false;
    }

}
