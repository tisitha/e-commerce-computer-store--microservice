package com.tisitha.user_service.exception;

public class EmailHasTakenException extends RuntimeException{
    public EmailHasTakenException(String message){
        super(message);
    }
}
