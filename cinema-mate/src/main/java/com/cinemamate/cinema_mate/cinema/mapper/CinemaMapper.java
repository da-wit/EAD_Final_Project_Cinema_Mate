package com.cinemamate.cinema_mate.cinema.mapper;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;

public class CinemaMapper {
    public static CinemaDto cinemaToCinemaDto(Cinema cinema) {
        return CinemaDto.builder()
                .id(cinema.getId())
                .cinemaName(cinema.getCinemaName())
                .email(cinema.getEmail())
                .description(cinema.getDescription())
                .isActive(cinema.isActive())
                .role(cinema.getRole())
                .build();
    }
    public static BasicCinemaDto cinemaToBasicCinemaDto(Cinema cinema) {
        return BasicCinemaDto.builder()
                .id(cinema.getId())
                .cinemaName(cinema.getCinemaName())
                .email(cinema.getEmail())
                .description(cinema.getDescription())
                .build();
    }

}
