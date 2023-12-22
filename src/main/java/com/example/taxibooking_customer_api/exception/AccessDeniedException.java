package com.example.taxibooking_customer_api.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String exception){
        super(exception);
    }
}
