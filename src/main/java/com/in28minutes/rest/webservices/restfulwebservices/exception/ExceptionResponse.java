package com.in28minutes.rest.webservices.restfulwebservices.exception;

import java.util.Date;

public class ExceptionResponse {

    private Date timestame;
    private String message;
    private String details;

    public Date getTimestame() {
        return timestame;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public ExceptionResponse(Date timestame, String message, String details) {
        this.timestame = timestame;
        this.message = message;
        this.details = details;
    }
}
