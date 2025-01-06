package com.cinemamate.cinema_mate.movie.dto;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
public class MovieDetailDto {
    private String id;
    private String title;
    private String description;
    private LocalTime duration;
    private LocalDate viewDate;
    private Long seats;
    private String imagePath;
    private BasicCinemaDto cinema;
}



