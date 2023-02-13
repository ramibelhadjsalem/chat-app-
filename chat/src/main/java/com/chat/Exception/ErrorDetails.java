package com.chat.Exception;

import java.util.Date;

public class ErrorDetails {
    private  String message ;
    private  String uri ;


    private Date timeStamp ;

    public ErrorDetails() {
        this.timeStamp = new Date() ;



    }

    public ErrorDetails(String message, String uri) {
        this();
        this.message = message;
        this.uri = uri;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}