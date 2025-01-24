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
    public static UserExceptions usernameNotFound() {
        String message = "User not found.";
        return new UserExceptions(message, null, "404", HttpStatus.NOT_FOUND);
    }
    public static UserExceptions usernameAlreadyTaken() {
        String message = "UserName already taken";
        return new UserExceptions(message, null, "409", HttpStatus.CONFLICT);
    }

    public static UserExceptions emailAlreadyRegistered() {
        String message = "Email already registered";
        return new UserExceptions(message, null, "409", HttpStatus.CONFLICT);
    }
    public static UserExceptions incorrectOldPassword() {
        String message = "The old password you entered is incorrect.";
        return new UserExceptions(message, null, "400", HttpStatus.BAD_REQUEST);
    }


    // Static factory method for "Bad Request" exception
    public static UserExceptions badRequest(String message) {
        return new UserExceptions(message, null, "400", HttpStatus.BAD_REQUEST);
    }

}

