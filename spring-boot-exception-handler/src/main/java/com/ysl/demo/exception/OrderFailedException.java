package com.ysl.demo.exception;

public class OrderFailedException extends RuntimeException {
    public OrderFailedException(String msg) {
        super(msg);
    }
}
