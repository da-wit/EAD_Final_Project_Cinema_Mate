package com.cinemamate.cinema_mate.cinema.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCinemaDto {

    @NotEmpty
    @Size(min = 3,message = "CinemaName can not be less than 3 characters.")
    private String cinemaName;
    @Email
    private String email;
    @NotEmpty
    private String description;
}
