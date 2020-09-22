package com.bruno.tinderclone.model.response;

public class Response<T> extends ResponseBase{

    private T data;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
