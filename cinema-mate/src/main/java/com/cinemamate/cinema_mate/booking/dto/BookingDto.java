package com.cinemamate.cinema_mate.booking.dto;

import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class BookingDto {
    private String id;
    private Long numberOfSeats;
    private String bookingCode;
    private LocalTime bookedAt;
    private MovieDetailDto movieDetailDto;
}
