package com.cinemamate.cinema_mate.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDto {
    @NotNull
    @Min(value = 0)
    @Min(value = 0, message = "Seats must be at least 1")
    private Long seats;
}
