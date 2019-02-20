package com.mvii3iv.sat.crawler.components.anticaptcha.models;


public class AntiCaptchaCreatedTaskResponse {
    private int errorId;
    private double taskId;

    public AntiCaptchaCreatedTaskResponse(int errorId, double taskId) {
        this.errorId = errorId;
        this.taskId = taskId;
    }

    public AntiCaptchaCreatedTaskResponse() {
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public double getTaskId() {
        return taskId;
    }

    public void setTaskId(double taskId) {
        this.taskId = taskId;
    }
}
