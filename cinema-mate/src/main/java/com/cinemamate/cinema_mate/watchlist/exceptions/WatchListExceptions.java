package com.cinemamate.cinema_mate.watchlist.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class WatchListExceptions extends RuntimeException{
    private final String message;
    private  final String errorCode;
    private final HttpStatus httpStatus;

    public WatchListExceptions(String message,String errorCode,HttpStatus httpStatus){
        super(message);
        this.message=message;
        this.errorCode=errorCode;
        this.httpStatus =httpStatus;
    }

    public static WatchListExceptions watchListNotFound(String id){
        String message = String.format("watchList with id: %s not found",id);
        return  new WatchListExceptions(message,"404",HttpStatus.NOT_FOUND);
    }

    public static WatchListExceptions alreadyInTheWatchList(){
        String message = "The movie is already in the watchList";
        return  new WatchListExceptions(message,"409",HttpStatus.CONFLICT);
    }
}
