package com.example.Exception;

public class ProductoNotFoundException  extends RuntimeException {

    public ProductoNotFoundException(String message) {
        super(message);
    }

    public ProductoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
