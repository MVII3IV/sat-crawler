package com.mvii3iv.sat.crawler.components.anticaptcha.models;


public class Solution {
    private String text;
    private String url;

    public Solution(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public Solution() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
