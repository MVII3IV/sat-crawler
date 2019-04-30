package com.mvii3iv.sat.crawler.components.bills;

import org.springframework.beans.factory.annotation.Autowired;
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

    public BillsController(BillsRepository billsRepository, BillsService billsService){
        this.billsRepository = billsRepository;
        this.billsService = billsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Bills> getBillsByRfcAndPass(@RequestParam String rfc){
        try {
            return billsService.getBillsByRFC(rfc);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
