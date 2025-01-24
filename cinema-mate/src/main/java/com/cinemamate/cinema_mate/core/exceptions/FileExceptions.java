package com.cinemamate.cinema_mate.core.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder

public class FileExceptions extends RuntimeException{
    private String message;
    private String statusCode;
    private HttpStatus httpStatus;

    public FileExceptions(String message,String statusCode,HttpStatus httpStatus){
        super(message);
        this.message = message;
        this.statusCode=statusCode;
        this.httpStatus = httpStatus;
    }

    public static FileExceptions noImageSelected(){
        String message = "No image provided";
        return new FileExceptions(message,"400",HttpStatus.BAD_REQUEST);
    }
    public static FileExceptions imageNotFound(){
        String message = "Image not found";
        return new FileExceptions(message,"404",HttpStatus.NOT_FOUND);
    }
    public static FileExceptions ioException(String message){

        return new FileExceptions(message,"500",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static FileExceptions failedToUploadImage(){
        String message = "Failed to upload image";
        return new FileExceptions(message,"500",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
