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
import java.util.Date;
import java.util.List;

@Service
public class BillsService {

    @Autowired
    Browser browser;
    private BillsRepository billsRepository;
    private CustomersService customersService;
    private List bills = new ArrayList<Bills>();

    public List getBills() {
        return bills;
    }

    public void setBills(List bills) {
        this.bills = bills;
    }

    /**
     * The class constructor
     * @param browser
     * @param billsRepository
     * @param customersService
     */
    public BillsService(Browser browser, BillsRepository billsRepository, CustomersService customersService) {
        this.browser = browser;
        this.billsRepository = billsRepository;
        this.customersService = customersService;
    }

    /**
     * This method gets all the Customers from the DB
     * goes through the list and one by one extract data from SAT
     */
    public void extractAllCustomersDataFromSat() {
        List<Customers> customers = customersService.getCustomers();
        for(Customers customer : customers){
                extractDataByUserFromSat(customer.getRfc(), customer.getPass());
        }
    }

    /**
     * Extracts data only by one Customer at the time
     * this method is even used by extractAllCustomersDataFromSat
     * @param rfc
     * @param pass
     * @return
     */
    public List<Bills> extractDataByUserFromSat(String rfc, String pass) {
        WebClient webClient = browser.login(rfc, pass);

        if(webClient != null){
            return browser.getUserData(webClient, rfc);
        }else{
            System.out.print(new Date() + " [ERROR] Could login to the site");
            return null;
        }
    }

    /**
     * Returns Bills by user rfc and password
     * @param rfc
     * @return
     * @throws IOException
     */
    public List<Bills> getBillsByRFC(String rfc) throws IOException {
        return billsRepository.findByUserId(rfc);
    }


}
