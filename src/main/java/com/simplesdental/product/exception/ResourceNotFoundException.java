package com.simplesdental.product.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super(resource.concat(" not found"));
    }
}
