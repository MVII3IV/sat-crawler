package com.mvii3iv.sat.crawler.components.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.mvii3iv.sat.crawler.components.bills.BillsService;
import com.mvii3iv.sat.crawler.components.browser.Browser;
import com.mvii3iv.sat.crawler.components.bills.Bills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value="/crawler")
public class CrawlerController {

    @Autowired
    Browser browser;

    @Autowired
    BillsService billsService;

    public CrawlerController(Browser browser, BillsService billsService) {
        this.browser = browser;
        this.billsService = billsService;
    }

    @RequestMapping(value = "/extract/data", method = RequestMethod.GET)
    public int getUserData(@RequestParam String rfc, @RequestParam String pass) throws IOException {
        billsService.extractDataByUserFromSat(rfc, pass);
        return 200;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public int getUserData() throws IOException {
        billsService.extractAllUserDataFromSat();
        return 200;
    }

}
