package com.mvii3iv.sat.crawler.components.anticaptcha.models;


public class Task {
    private String type;
    private String body;
    private boolean phrase;
    private boolean caseSensitive;
    private boolean numeric;
    private int math;
    private int minLength;
    private int maxLength;

    public Task(String type, String body, boolean phrase, boolean caseSensitive, boolean numeric, int math, int minLength, int maxLength) {
        this.type = type;
        this.body = body;
        this.phrase = phrase;
        this.caseSensitive = caseSensitive;
        this.numeric = numeric;
        this.math = math;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPhrase() {
        return phrase;
    }

    public void setPhrase(boolean phrase) {
        this.phrase = phrase;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean isNumeric() {
        return numeric;
    }

    public void setNumeric(boolean numeric) {
        this.numeric = numeric;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
