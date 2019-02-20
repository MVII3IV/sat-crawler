package com.mvii3iv.sat.crawler.components.anticaptcha.models;


public class AntiCaptchaCreateTask {
    private String clientKey;
    private Task task;

    public AntiCaptchaCreateTask(String clientKey, Task task) {
        this.clientKey = clientKey;
        this.task = task;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
