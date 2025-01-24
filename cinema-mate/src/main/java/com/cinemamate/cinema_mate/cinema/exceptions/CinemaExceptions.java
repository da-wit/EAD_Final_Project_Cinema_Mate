package com.cinemamate.cinema_mate.cinema.exceptions;

import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class CinemaExceptions extends RuntimeException {
    private final String message;
    private final Throwable throwable;
    private final String errorCode;
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

    public CinemaExceptions(String message, Throwable throwable, String errorCode, HttpStatus httpStatus) {
        super(message); // Pass message to RuntimeException
        this.message = message;
        this.throwable = throwable;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = ZonedDateTime.now(); // Automatically set the timestamp
    }

    public static CinemaExceptions notFound(String resourceId) {
        String message = String.format("Cinema with ID %s not found.", resourceId);
        return new CinemaExceptions(message, null, "404", HttpStatus.NOT_FOUND);
    }

    public static CinemaExceptions cinemaNameNotFound(String cinemaName) {
        String message = String.format("Cinema with cinemaName %s not found.", cinemaName);
        return new CinemaExceptions(message, null, "404", HttpStatus.NOT_FOUND);
    }

    public static CinemaExceptions cinemaNameAlreadyTaken(){
        String message = "Cinema name already taken";
        return  new CinemaExceptions(message,null,"409",HttpStatus.CONFLICT);
    }

    public static CinemaExceptions emailAlreadyRegistered(){
        String message = "Email already registered";
        return  new CinemaExceptions(message,null,"409",HttpStatus.CONFLICT);
    }

    public static CinemaExceptions incorrectOldPassword() {
        String message = "The old password you entered is incorrect.";
        return new CinemaExceptions(message, null, "400", HttpStatus.BAD_REQUEST);
    }

    // Static factory method for "Bad Request" exception
    public static CinemaExceptions badRequest(String message) {
        return new CinemaExceptions(message, null, "400", HttpStatus.BAD_REQUEST);
    }

}

