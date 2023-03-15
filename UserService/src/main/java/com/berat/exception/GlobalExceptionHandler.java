package com.berat.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorMessage createErrorMessage(EErrorType errorType,Exception exception){
        System.out.println("Hata oluştu: "+exception.getMessage());
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
        return ResponseEntity.ok("Beklenmeyen bir hata olustu: "+ex.getMessage());
    }
    @ExceptionHandler(UserManagerException.class)
    public ResponseEntity<ErrorMessage> handleManagerException(UserManagerException exception){
        EErrorType errorType=exception.getErrorType();
        HttpStatus httpStatus=errorType.httpStatus;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),httpStatus);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        EErrorType errorType=EErrorType.USERNAME_DUPLICATE;
        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),httpStatus);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        EErrorType errorType=EErrorType.INVALID_PARAMETER_ERROR;
        List<String> fields=new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(e-> fields.add(e.getField()+": "+e.getDefaultMessage()));
        ErrorMessage errorMessage=createErrorMessage(errorType,exception);
        errorMessage.setField(fields);
        return new ResponseEntity<>(errorMessage,errorType.getHttpStatus());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorMessage> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        EErrorType errorType = EErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public final ResponseEntity<ErrorMessage> handleInvalidFormatException(
            InvalidFormatException exception) {
        EErrorType errorType = EErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MethodArgumentTypeMismatchException exception) {
        EErrorType errorType = EErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MissingPathVariableException exception) {
        EErrorType errorType = EErrorType.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleAllExceptions(Exception exception) {
        EErrorType errorType = EErrorType.INTERNAL_ERROR;
        List<String> fields = new ArrayList<>();
        fields.add(exception.getMessage());
        ErrorMessage errorMessage = createErrorMessage(errorType, exception);
        errorMessage.setField(fields);
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }
}
