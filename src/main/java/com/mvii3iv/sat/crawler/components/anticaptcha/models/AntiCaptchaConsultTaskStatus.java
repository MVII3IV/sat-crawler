package com.mvii3iv.sat.crawler.components.anticaptcha.models;


public class AntiCaptchaConsultTaskStatus {
    private String clientKey;
    private double taskId;

    public AntiCaptchaConsultTaskStatus(String clientKey, double taskId) {
        this.clientKey = clientKey;
        this.taskId = taskId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public double getTaskId() {
        return taskId;
    }

    public void setTaskId(double taskId) {
        this.taskId = taskId;
    }
}
