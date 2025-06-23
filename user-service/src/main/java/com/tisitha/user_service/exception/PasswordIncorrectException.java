package com.tisitha.user_service.exception;

public class PasswordIncorrectException extends RuntimeException{

    public PasswordIncorrectException(String message){
        super(message);
    }
}
