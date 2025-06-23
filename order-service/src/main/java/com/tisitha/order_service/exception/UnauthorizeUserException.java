package com.tisitha.order_service.exception;

public class UnauthorizeUserException extends RuntimeException{
    public UnauthorizeUserException(String message){
        super(message);
    }
}
