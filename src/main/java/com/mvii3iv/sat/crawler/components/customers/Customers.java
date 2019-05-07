package com.mvii3iv.sat.crawler.components.customers;


import org.springframework.data.annotation.Id;

public class Customers {

    private String rfc;
    private String pass;

    public Customers(String rfc, String pass) {
        this.rfc = rfc;
        this.pass = pass;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
