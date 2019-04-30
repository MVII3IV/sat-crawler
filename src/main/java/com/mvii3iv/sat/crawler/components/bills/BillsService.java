package com.mvii3iv.sat.crawler.components.bills;


import com.gargoylesoftware.htmlunit.WebClient;
import com.mvii3iv.sat.crawler.components.browser.Browser;
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

    public List getBills() {
        return bills;
    }

    public void setBills(List bills) {
        this.bills = bills;
    }

    private List bills = new ArrayList<Bills>();

    public List<Bills> getUserDataByRfcAndPass(String rfc, String pass) throws IOException {
        WebClient webClient = browser.login(rfc, pass);
        return browser.getUserData(webClient, rfc);
    }


}
