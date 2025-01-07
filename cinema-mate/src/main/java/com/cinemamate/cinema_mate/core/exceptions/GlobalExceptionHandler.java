package com.cinemamate.cinema_mate.core.exceptions;

import com.cinemamate.cinema_mate.admin.exceptions.AuthExceptions;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.core.entity.ErrorDetail;
import com.cinemamate.cinema_mate.movie.exceptions.MovieExceptions;
import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import com.cinemamate.cinema_mate.watchlist.exceptions.WatchListExceptions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthExceptions.class)
    public ResponseEntity<ErrorDetail> handlerAuthExceptions(AuthExceptions e, WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(),webRequest.getDescription(false),new Date());

        return new ResponseEntity<>(errorDetail, e.getHttpStatus());
    }

    @ExceptionHandler(UserExceptions.class)
    public ResponseEntity<ErrorDetail> handlerUserExceptions(UserExceptions e, WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(),webRequest.getDescription(false),new Date());

        return new ResponseEntity<>(errorDetail, e.getHttpStatus());
    }

    @ExceptionHandler(CinemaExceptions.class)
    public ResponseEntity<ErrorDetail> cinemaExceptionHandler(CinemaExceptions e,WebRequest webRequest){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(e.getMessage())
                .details(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();
        return  new ResponseEntity<>(errorDetail,e.getHttpStatus());
    }

    @ExceptionHandler(MovieExceptions.class)
    public ResponseEntity<ErrorDetail> movieExceptionHandler(MovieExceptions e,WebRequest webRequest){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(e.getMessage())
                .details(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();
        return  new ResponseEntity<>(errorDetail,e.getHttpStatus());
    }

    @ExceptionHandler(WatchListExceptions.class)
    public ResponseEntity<ErrorDetail> watchListExceptionHandler(WatchListExceptions e,WebRequest webRequest){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(e.getMessage())
                .details(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();
        return  new ResponseEntity<>(errorDetail,e.getHttpStatus());
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
        });

        return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalExceptions(Exception e, WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                e.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
