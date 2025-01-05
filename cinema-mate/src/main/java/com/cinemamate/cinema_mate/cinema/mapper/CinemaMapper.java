package com.cinemamate.cinema_mate.cinema.mapper;

import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;

public class CinemaMapper {
    public static CinemaDto cinemaToCinemaDto(Cinema cinema) {
        return CinemaDto.builder()
                .cinemaname(cinema.getCinemaname())
                .email(cinema.getEmail())
                .description(cinema.getDescription())
                .isActive(cinema.isActive())
                .role(cinema.getRole())
                .build();
    }
}
