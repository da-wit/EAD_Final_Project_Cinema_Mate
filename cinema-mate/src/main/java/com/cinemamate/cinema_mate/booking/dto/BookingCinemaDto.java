package com.cinemamate.cinema_mate.booking.dto;

import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
@Data
@Builder
public class BookingCinemaDto {
    private String id;
    private String userName;
    private String imagePath;
    private long bookedSeats;
    private BigDecimal totalPrice;
    private LocalTime bookedAt;
    private MovieDto movieDto;
}
