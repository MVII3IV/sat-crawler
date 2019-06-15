package com.mvii3iv.sat.crawler.components.employee;


import org.springframework.data.annotation.Id;

public class Employees {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String secondLastName;
    private String securityNumber;
    private String curp;
    private String dailySalary;
    private String bossRfc;
    private int statusId;


    public Employees() {
    }

    public Employees(String id, String name, String lastName, String secondLastName, String securityNumber, String curp, String dailySalary, String bossRfc, int statusId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.securityNumber = securityNumber;
        this.curp = curp;
        this.dailySalary = dailySalary;
        this.bossRfc = bossRfc;
        this.statusId = statusId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getDailySalary() {
        return dailySalary;
    }

    public void setDailySalary(String dailySalary) {
        this.dailySalary = dailySalary;
    }

    public String getBossRfc() {
        return bossRfc;
    }

    public void setBossRfc(String bossRfc) {
        this.bossRfc = bossRfc;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
