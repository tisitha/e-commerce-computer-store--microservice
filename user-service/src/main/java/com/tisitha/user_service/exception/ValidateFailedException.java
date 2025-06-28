package com.tisitha.user_service.exception;

public class ValidateFailedException extends RuntimeException{

    public ValidateFailedException(String message){
        super(message);
    }
}
