package com.tisitha.order_service.exception;

public class StockOutException extends RuntimeException{

    public StockOutException(String message){
        super(message);
    }
}
