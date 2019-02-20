package com.mvii3iv.sat.crawler.components.anticaptcha.models;

import java.sql.Time;

public class AntiCaptchaTaskResult {
    private int errorId;
    private String status;
    private Solution solution;
    private String cost;
    private String ip;
    private Time createTime;
    private Time endTime;
    private int solveCount;

    public AntiCaptchaTaskResult(int errorId, String status, Solution solution, String cost, Time createTime, Time endTime, int solveCount, String ip) {
        this.errorId = errorId;
        this.status = status;
        this.solution = solution;
        this.cost = cost;
        this.createTime = createTime;
        this.endTime = endTime;
        this.solveCount = solveCount;
        this.ip = ip;
    }

    public AntiCaptchaTaskResult() {
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getSolveCount() {
        return solveCount;
    }

    public void setSolveCount(int solveCount) {
        this.solveCount = solveCount;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
