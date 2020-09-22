package com.bruno.tinderclone.model.response;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class ResponseBase extends RepresentationModel<ResponseBase> {

    private Object errors;
    private Integer statusCode;

    public void addErrorMessage(String message) {
        ResponseError error = new ResponseError()
                .setDetails(message)
                .setTimestamp(LocalDateTime.now());
        setErrors(error);
    }

    public void addStatusCode(Integer statusCode){
        this.statusCode = statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

}
