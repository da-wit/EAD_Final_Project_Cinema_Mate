package com.cinemamate.cinema_mate.movie.dto;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class MovieDetailDto {
    private String id;
    private String title;
    private String description;
    private LocalTime duration;
    private LocalTime viewTime;
    private LocalDate viewDate;
    private long seats;
    private long bookedSeats;
    private BigDecimal price;
    private List<String> genres;
    private String imagePath;
    private boolean isActive;
    private boolean alreadyBooked;
    private String alreadyInTheWatchList;
    private BasicCinemaDto cinema;
}



