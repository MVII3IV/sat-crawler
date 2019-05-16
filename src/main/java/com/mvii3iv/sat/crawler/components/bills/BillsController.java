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

    @Autowired
    private BillsRepository billsRepository;

    @Autowired
    private BillsService billsService;

    @Autowired
    private CustomersService customersService;

    @Autowired
    MongoTemplate mongoTemplate;

    public BillsController(BillsRepository billsRepository, BillsService billsService, CustomersService customersService, MongoTemplate mongoTemplate) {
        this.billsRepository = billsRepository;
        this.billsService = billsService;
        this.customersService = customersService;
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Bills> getBillsByRfcAndPass(@RequestParam String rfc, @RequestParam String pass){
        try {

            //List<String> userIds = mongoTemplate.getCollection("bills").distinct("userId");

            if(rfc.isEmpty() || pass.isEmpty())
                return null;

            rfc = rfc.trim();
            pass = pass.trim();

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

}
