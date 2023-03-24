package com.berat.exception;

import lombok.Getter;

@Getter
public class ElasticServiceManagerException extends RuntimeException{

    private final EErrorType errorType;

    public ElasticServiceManagerException(EErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public ElasticServiceManagerException(EErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
