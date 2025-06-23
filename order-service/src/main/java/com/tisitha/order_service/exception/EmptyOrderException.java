package com.tisitha.order_service.exception;

public class EmptyOrderException extends RuntimeException{

    public EmptyOrderException(String message){
        super(message);
    }
}
