package com.berat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum EErrorType {

    INTERNAL_ERROR(5100,"Sunucu hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre hatası",HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4110,"Kullanıcı adı ya da şifre hatalı",HttpStatus.BAD_REQUEST),
    DATABASE_ERROR(4111,"Username zaten mevcut",HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER_ERROR(4112,"Parametre hatası",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4113,"User bulunamadı",HttpStatus.NOT_FOUND ),
    ACTIVATE_CODE_ERROR(4114,"Aktivasyon kod hatası",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4115,"Geçersiz token",HttpStatus.BAD_REQUEST),
    NOT_ACTIVE_ACCOUNT(4116,"Hesap aktif değil",HttpStatus.FORBIDDEN),
    TOKEN_NOT_CREATED(4117,"Token oluşturulamadı",HttpStatus.FORBIDDEN),
    USER_NOT_CREATED(4117,"Userprofile oluşturulamadı",HttpStatus.FORBIDDEN),


    ;

    private int code;
    private String message;
    HttpStatus httpStatus;

}
