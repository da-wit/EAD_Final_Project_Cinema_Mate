package com.cinemamate.cinema_mate.movie.exceptions;

import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class MovieExceptions extends RuntimeException {
    private final String message;
    private  final String errorCode;
    private final HttpStatus httpStatus;

    public MovieExceptions(String message,String errorCode, HttpStatus httpStatus) {
        super(message); // Pass message to RuntimeException
        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public static MovieExceptions movieAlreadyExist(String title){
        String message = String.format("movie with title: %s already exists",title);
        return  new MovieExceptions(message,"409",HttpStatus.CONFLICT);
    }

    public static MovieExceptions movieNotFound(String id){
        String message = String.format("movie with id: %s not found",id);
        return  new MovieExceptions(message,"404",HttpStatus.NOT_FOUND);
    }

}
