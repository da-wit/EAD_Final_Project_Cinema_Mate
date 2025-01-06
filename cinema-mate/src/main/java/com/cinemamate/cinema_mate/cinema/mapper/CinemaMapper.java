package com.cinemamate.cinema_mate.cinema.mapper;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import jakarta.persistence.Basic;

public class CinemaMapper {
    public static CinemaDto cinemaToCinemaDto(Cinema cinema) {
        return CinemaDto.builder()
                .id(cinema.getId())
                .cinemaname(cinema.getCinemaname())
                .email(cinema.getEmail())
                .description(cinema.getDescription())
                .isActive(cinema.isActive())
                .role(cinema.getRole())
                .build();
    }
    public static BasicCinemaDto cinemaToBasicCinemaDto(Cinema cinema) {
        return BasicCinemaDto.builder()
                .id(cinema.getId())
                .cinemaname(cinema.getCinemaname())
                .email(cinema.getEmail())
                .description(cinema.getDescription())
                .build();
    }
}
