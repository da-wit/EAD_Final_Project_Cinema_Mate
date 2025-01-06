package com.cinemamate.cinema_mate.cinema.dto;

import com.cinemamate.cinema_mate.core.constant.Role;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BasicCinemaDto {
    private String id;
    private String cinemaname;
    private String email;
    private String description;
}
