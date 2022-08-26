package com.geektrust.backend.exceptions;

public class IncorrectPaymentException extends RuntimeException{
    public IncorrectPaymentException(){
        super();
    }
    public IncorrectPaymentException(String msg){
        super(msg);
    }
}
