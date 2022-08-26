package com.geektrust.backend.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(){
        super();
    }
    public MemberNotFoundException(String msg){
        super(msg);
    }
}
