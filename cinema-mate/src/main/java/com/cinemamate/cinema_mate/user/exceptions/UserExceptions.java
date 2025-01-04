package com.cinemamate.cinema_mate.user.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class UserExceptions extends RuntimeException {
    private final String message;
    private  final Throwable throwable;
    private  final String errorCode;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

//    public static CommentException notFound(String resourceName, String resourceId) {
//        return CommentException.builder()
//                .message(String.format("%s with ID %s not found.", resourceName, resourceId))
//                .errorCode("404")
//                .httpStatus(HttpStatus.NOT_FOUND)
//                .timestamp(ZonedDateTime.now())
//                .build();
//    }
//
//    public  static CommentException badRequest(String message){
//        return CommentException.builder()
//                .message(message)
//                .errorCode("400")
//                .httpStatus(HttpStatus.BAD_REQUEST)
//                .timestamp(ZonedDateTime.now())
//                .build();
//    }

    public UserExceptions(String message, Throwable throwable, String errorCode, HttpStatus httpStatus) {
        super(message); // Pass message to RuntimeException
        this.message = message;
        this.throwable = throwable;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = ZonedDateTime.now(); // Automatically set the timestamp
    }

    public static UserExceptions notFound(String resourceId) {
        String message = String.format("User with ID %s not found.", resourceId);
        return new UserExceptions(message, null, "404", HttpStatus.NOT_FOUND);
    }
    public static UserExceptions usernameNotFound(String username) {
        String message = String.format("User with username %s not found.", username);
        return new UserExceptions(message, null, "404", HttpStatus.NOT_FOUND);
    }

    // Static factory method for "Bad Request" exception
    public static UserExceptions badRequest(String message) {
        return new UserExceptions(message, null, "400", HttpStatus.BAD_REQUEST);
    }

}

