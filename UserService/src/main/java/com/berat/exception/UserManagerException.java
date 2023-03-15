package com.berat.exception;

import lombok.Getter;

@Getter
public class UserManagerException extends RuntimeException{

    private final EErrorType errorType;

    public UserManagerException(EErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public UserManagerException(EErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
