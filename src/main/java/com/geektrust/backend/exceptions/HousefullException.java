package com.geektrust.backend.exceptions;

public class HousefullException extends RuntimeException {
    public HousefullException(){
        super();
    }
    public HousefullException(String msg){
        super(msg);
    }
}
