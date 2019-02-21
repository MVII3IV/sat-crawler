package com.mvii3iv.sat.crawler.components.crawler;

import com.mvii3iv.sat.crawler.components.browser.Browser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value="/crawler")
public class CrawlerController {

    @Autowired
    Browser browser;

    public CrawlerController(Browser browser) {
        this.browser = browser;
    }

    @RequestMapping(value = "/extract/emitted", method = RequestMethod.GET)
    public String getEmittedBills() throws IOException {
        browser.login();
        return "hello";
    }

}
