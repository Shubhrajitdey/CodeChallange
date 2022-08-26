package com.geektrust.backend.exceptions;

public class MoveoutFailureException extends RuntimeException{
    public MoveoutFailureException(){
        super();
    }
    public MoveoutFailureException(String msg){
        super(msg);
    }

}
