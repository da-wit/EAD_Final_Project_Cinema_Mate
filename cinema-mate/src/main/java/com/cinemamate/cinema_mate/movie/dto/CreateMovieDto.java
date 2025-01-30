package com.cinemamate.cinema_mate.movie.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class CreateMovieDto {
    @NotEmpty(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotEmpty(message = "Description is required")
    @Size(max = 10000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Duration is required")
    private LocalTime duration;

    @NotNull(message = "View time is required")
    private LocalTime viewTime;

    @NotNull(message = "View date is required")
    @FutureOrPresent(message = "View date cannot be in the past")
    private LocalDate viewDate;

    @NotNull(message = "Seats are required")
    @Min(value = 1, message = "Seats must be at least 1")
    private long seats;
    @NotNull(message = "Price is required")
    private BigDecimal price;
    @NotNull(message = "genres is required")
    private List<String> genres;
}
