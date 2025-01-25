package com.cinemamate.cinema_mate.booking.dto;

import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Builder
public class BookingDto {
    private String id;
    private long numberOfSeats;
    private BigDecimal totalPrice;
    private String bookingCode;
    private LocalTime bookedAt;
    private MovieDetailDto movieDetailDto;
}
