package com.berat.exception;

import lombok.Getter;

@Getter
public class AuthManagerException extends RuntimeException{

    private final EErrorType errorType;

    public AuthManagerException(EErrorType errorType,String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public AuthManagerException(EErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
