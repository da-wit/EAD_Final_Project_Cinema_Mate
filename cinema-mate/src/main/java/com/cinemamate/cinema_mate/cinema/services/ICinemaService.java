package com.cinemamate.cinema_mate.cinema.services;

import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;

public interface ICinemaService {
    CinemaDto getCinemaByCinemaName(String cinemaName);
    boolean cinemaExists(String cinemaName);
    Cinema getCinema(String cinemaName);
    CinemaDto saveCinema(Cinema cinema);
    Cinema getCinemaById(String id);
}
