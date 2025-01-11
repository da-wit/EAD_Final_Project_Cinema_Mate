package com.cinemamate.cinema_mate.core.util;

import java.security.SecureRandom;

public class BookingCodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private  final int CODE_LENGTH = 6;

    public  static  String generateBookingCode(){
        int code = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(code);
    }

}
