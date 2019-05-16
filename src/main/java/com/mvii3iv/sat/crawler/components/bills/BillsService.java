package com.mvii3iv.sat.crawler.components.bills;


import com.gargoylesoftware.htmlunit.WebClient;
import com.mvii3iv.sat.crawler.components.browser.Browser;
import com.mvii3iv.sat.crawler.components.customers.Customers;
import com.mvii3iv.sat.crawler.components.customers.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillsService {

    @Autowired
    Browser browser;

    @Autowired
    private BillsRepository billsRepository;

    @Autowired
    private CustomersService customersService;

    private List bills = new ArrayList<Bills>();

    public List getBills() {
        return bills;
    }

    public void setBills(List bills) {
        this.bills = bills;
    }

    public BillsService(Browser browser, BillsRepository billsRepository, CustomersService customersService) {
        this.browser = browser;
        this.billsRepository = billsRepository;
        this.customersService = customersService;
    }

    public void extractAllUserDataFromSat() {
        List<Customers> customers = customersService.getCustomers();
        for(Customers customer : customers){
                extractDataByUserFromSat(customer.getRfc(), customer.getPass());
        }
    }

    public List<Bills> extractDataByUserFromSat(String rfc, String pass) {
        WebClient webClient = browser.login(rfc, pass);
        return browser.getUserData(webClient, rfc);
    }

    public List<Bills> getBillsByRFC(String rfc) throws IOException {
        return billsRepository.findByUserId(rfc);
    }


}
