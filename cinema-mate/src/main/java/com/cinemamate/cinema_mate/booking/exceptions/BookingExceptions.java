package com.cinemamate.cinema_mate.booking.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BookingExceptions extends RuntimeException{
    private final String message;
    private  final String errorCode;
    private final HttpStatus httpStatus;

    public BookingExceptions (String message,String errorCode,HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public static BookingExceptions notFound(String booingId){
        String message = String.format("Booking with this id: %s not found",booingId);
        return new BookingExceptions(message,"404",HttpStatus.NOT_FOUND);
    }
    public static BookingExceptions noAvailableSeats(){
        String message = "All seats have been booked out";
        return new BookingExceptions(message,"409",HttpStatus.CONFLICT);
    }
    public static BookingExceptions inCorrectVerificationCode(){
        String message = "Incorrect verification code";
        return new BookingExceptions(message,"400",HttpStatus.BAD_REQUEST);
    }

    public static BookingExceptions insufficientSeatsAvailable(){
        String message = "Requested seats exceed the available seats.";
        return new BookingExceptions(message,"409",HttpStatus.CONFLICT);
    }
    public static BookingExceptions bookingCodeNotFound(String code){
        String message = String.format("Booking with code: %s not found",code);
        return new BookingExceptions(message,"404",HttpStatus.NOT_FOUND);
    }



}
