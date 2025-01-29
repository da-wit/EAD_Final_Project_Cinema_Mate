package com.cinemamate.cinema_mate.cinema.dto;

import com.cinemamate.cinema_mate.core.constant.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CinemaDto {
    private String id;
    private String cinemaName;
    private String email;
    private String description;
    private String imagePath;
    private boolean isActive;
    private Role role;
}
