package com.cinemamate.cinema_mate.auth.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AuthExceptions extends RuntimeException {

    private final String message;
    private  final String errorCode;
    private final HttpStatus httpStatus;

    public AuthExceptions (String message,String errorCode,HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public static AuthExceptions invalidCredentials(){
        String message = "Invalid username or password";
        return new AuthExceptions(message,"401",HttpStatus.UNAUTHORIZED);
    }

    public static AuthExceptions invalidToken(){
        String message = "Invalid Token";
        return new AuthExceptions(message,"401",HttpStatus.UNAUTHORIZED);
    }

    public static AuthExceptions expiredToken(){
        String message = "Token Expired";
        return new AuthExceptions(message,"401",HttpStatus.UNAUTHORIZED);
    }

    public static AuthExceptions userNameAlreadyTaken() {
        String message = "Username already taken.";
        return new AuthExceptions(message, "409", HttpStatus.CONFLICT);
    }

}