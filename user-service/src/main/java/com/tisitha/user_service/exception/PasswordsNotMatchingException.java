package com.tisitha.user_service.exception;

public class PasswordsNotMatchingException extends RuntimeException{

    public PasswordsNotMatchingException(String message){
        super(message);
    }
}
