package com.mvii3iv.sat.crawler.components.records;

import java.util.Date;

public class BillsRecords {

    private String userId;
    private boolean emitted;
    private int billsQuantity;
    private String date;

    public BillsRecords() {
    }

    public BillsRecords(String userId, boolean emitted, int billsQuantity, String date) {
        this.userId = userId;
        this.emitted = emitted;
        this.billsQuantity = billsQuantity;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getEmitted() {
        return emitted;
    }

    public void setEmitted(boolean emitted) {
        this.emitted = emitted;
    }

    public int getQuantity() {
        return billsQuantity;
    }

    public void setQuantity(int quantity) {
        this.billsQuantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
