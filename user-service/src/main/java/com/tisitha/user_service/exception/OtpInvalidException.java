package com.tisitha.user_service.exception;

public class OtpInvalidException extends RuntimeException{

    public OtpInvalidException(String message){
        super(message);
    }
}
