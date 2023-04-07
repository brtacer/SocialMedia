package com.berat.exception;

import lombok.Getter;

@Getter
public class PostManagerException extends RuntimeException{

    private final EErrorType errorType;

    public PostManagerException(EErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public PostManagerException(EErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
