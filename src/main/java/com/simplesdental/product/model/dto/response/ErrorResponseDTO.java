package com.simplesdental.product.model.dto.response;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

public class ErrorResponseDTO {

    private final long timestamp;
    private final int status;
    private final String error;
    private final String path;
    private final Map<String, String> fields;

    public ErrorResponseDTO(HttpStatus status, String path, Map<String, String> fields) {
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.path = path;
        this.fields = fields;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getFields() {
        return fields;
    }
}
