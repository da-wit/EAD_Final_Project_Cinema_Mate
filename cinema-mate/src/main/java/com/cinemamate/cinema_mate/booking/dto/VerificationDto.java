package com.cinemamate.cinema_mate.booking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerificationDto {
    @NotEmpty(message = "userName can not be empty")
    private String userName;
    @NotEmpty(message = "BookingCode can not be empty")
    private String bookingCode;
}
