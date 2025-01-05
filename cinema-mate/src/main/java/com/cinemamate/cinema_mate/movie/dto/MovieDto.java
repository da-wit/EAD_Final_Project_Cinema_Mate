package com.cinemamate.cinema_mate.movie.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
public class MovieDto {
    private String id;
    private String title;
    private String description;
    private LocalTime duration;
    private LocalDate viewDate;
    private Long seats;
    private String imagePath;
}


