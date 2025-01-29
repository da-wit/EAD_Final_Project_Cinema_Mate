package com.cinemamate.cinema_mate.cinema.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicCinemaDto {
    private String id;
    private String cinemaName;
    private String email;
    private String description;
    private String imagePath;
}
