package com.simplesdental.product.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

public class ErrorResponseDTO {

    private final long timestamp;
    private final int status;
    private final String error;
    private final String path;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> fields;

    public ErrorResponseDTO(HttpStatus status, String path, Map<String, String> fields) {
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.path = path;
        this.message = null;
        this.fields = fields;
    }

    public ErrorResponseDTO(HttpStatus status, String path, String message) {
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.path = path;
        this.message = message;
        this.fields = null;
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

    public String getMessage() {
        return message;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        if (message != null) {
            return "{" +
                    "timestamp=" + timestamp +
                    ", status=" + status +
                    ", error='" + error + '\'' +
                    ", path='" + path + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
        return "{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", path='" + path + '\'' +
                ", fields=" + fields +
                '}';
    }
}
