package com.mvii3iv.sat.crawler.components.bills;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillsService {


    public List getBills() {
        return bills;
    }

    public void setBills(List bills) {
        this.bills = bills;
    }

    private List bills = new ArrayList<Bills>();
}
