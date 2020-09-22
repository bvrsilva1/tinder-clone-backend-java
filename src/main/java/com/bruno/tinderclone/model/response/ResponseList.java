package com.bruno.tinderclone.model.response;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseList<T> extends ResponseBase {

    private List<T> data;

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
