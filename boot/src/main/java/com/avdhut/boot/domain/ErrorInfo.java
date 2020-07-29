package com.avdhut.boot.domain;

public class ErrorInfo {

    private String url;
    private String message;

    public ErrorInfo(String url, String msg){
        this.url = url;
        this.message = msg;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}
