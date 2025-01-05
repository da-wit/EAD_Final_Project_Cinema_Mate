package com.cinemamate.cinema_mate.admin.exceptions;

import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Data
public class AuthExceptions extends RuntimeException {
    private final String message;
    private final Throwable throwable;
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public AuthExceptions(String message, Throwable throwable, String errorCode, HttpStatus httpStatus) {
        super(message); // Pass message to RuntimeException
        this.message = message;
        this.throwable = throwable;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = ZonedDateTime.now(); // Automatically set the timestamp
    }

    public static AuthExceptions userNameAlreadyTaken() {
        String message = "Username already taken.";
        return new AuthExceptions(message, null, "409", HttpStatus.CONFLICT);
    }

//    public static AuthExceptions notFound(String resourceId) {
//        String message = String.format("Cinema with ID %s not found.", resourceId);
//        return new AuthExceptions(message, null, "404", HttpStatus.NOT_FOUND);
//    }

//    public static AuthExceptions cinemaNameNotFound(String cinemaName) {
//        String message = String.format("Cinema with cinemaName %s not found.", cinemaName);
//        return new AuthExceptions(message, null, "404", HttpStatus.NOT_FOUND);
//    }
//
//    // Static factory method for "Bad Request" exception
//    public static CinemaExceptions badRequest(String message) {
//        return new AuthExceptions(message, null, "400", HttpStatus.BAD_REQUEST);
//    }

}