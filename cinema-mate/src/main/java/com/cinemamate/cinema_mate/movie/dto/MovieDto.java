package com.cinemamate.cinema_mate.movie.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class MovieDto {
    private String id;
    private String title;
    private String description;
    private LocalTime duration;
    private LocalTime viewTime;
    private LocalDate viewDate;
    private long seats;
    private BigDecimal price;
    private List<String> genres;
    private String imagePath;
    private boolean isActive;
}


