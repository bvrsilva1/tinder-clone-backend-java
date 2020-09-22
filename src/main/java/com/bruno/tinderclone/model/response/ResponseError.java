package com.bruno.tinderclone.model.response;

import java.time.LocalDateTime;

public class ResponseError {

    private LocalDateTime timestamp;
    private String details;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    public ResponseError setDetails(String details) {
        this.details = details;
        return this;
    }

    public ResponseError setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
