package com.cinemamate.cinema_mate.movie.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class UpdateMovieDto {
    @NotEmpty(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotEmpty(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
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
    private Long seats;
}
