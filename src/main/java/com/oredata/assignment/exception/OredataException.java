package com.oredata.assignment.exception;

import lombok.Getter;


@Getter
public class OredataException extends RuntimeException {
    private final ErrorType errorType;
    public OredataException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public OredataException(ErrorType errorType, String message){
        super(message);
        this.errorType = errorType;
    }


}
