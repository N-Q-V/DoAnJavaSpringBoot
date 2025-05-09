package com.example.fashion_spring_boot.exception;

public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}
