package com.mvii3iv.sat.crawler.components.bills;

import com.mvii3iv.sat.crawler.components.customers.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value="/bills")
public class BillsController {

    /**
     * Variables used by the class
     */
    @Autowired
    private MongoTemplate mongoTemplate;
    private BillsRepository billsRepository;
    private BillsService billsService;
    private CustomersService customersService;


    /**
     *
     * @param billsRepository
     * @param billsService
     * @param customersService
     * @param mongoTemplate
     */
    public BillsController(BillsRepository billsRepository, BillsService billsService, CustomersService customersService, MongoTemplate mongoTemplate) {
        this.billsRepository = billsRepository;
        this.billsService = billsService;
        this.customersService = customersService;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Returns the bills by rfc and pass
     * if there is information in the database returns that info
     * otherwise go to SAT's site and extract info and returns it
     * @param rfc
     * @param pass
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Bills> getBillsByRfcAndPass(@RequestParam String rfc, @RequestParam String pass){
        try {
            if(rfc.isEmpty() || pass.isEmpty())
                return null;

            rfc = rfc.trim();
            pass = pass.trim();

            //looks if the customer is already on the database, if it doesn't then it is added to customers
            customersService.validateCustomer(rfc, pass);

            List<Bills> bills = billsService.getBillsByRFC(rfc);

            if(bills.size() > 0)
                return bills;
            else
                return billsService.extractDataByUserFromSat(rfc, pass);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value="/extract", method = RequestMethod.GET)
    public boolean extractAllBills() {
        try {
            billsService.extractAllCustomersDataFromSat();
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
