package com.cinemamate.cinema_mate.booking.dto;

import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
@Data
@Builder
public class BookingCinemaDto {
    private String id;
    private String userName;
    private LocalTime bookedAt;
    private MovieDto movieDto;
}
