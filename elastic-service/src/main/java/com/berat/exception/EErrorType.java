package com.berat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum EErrorType {

    INTERNAL_ERROR(5200,"Sunucu hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4200,"Parametre hatası",HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4211,"Username zaten mevcut",HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER_ERROR(4212,"Parametre hatası",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4213,"User bulunamadı",HttpStatus.NOT_FOUND ),
    ACTIVATE_CODE_ERROR(4214,"Aktivasyon kod hatası",HttpStatus.BAD_REQUEST),
    USER_NOT_CREATED(4215,"Kullanıcı oluşturulamadı",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4216,"Geçersiz token",HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    HttpStatus httpStatus;

}
