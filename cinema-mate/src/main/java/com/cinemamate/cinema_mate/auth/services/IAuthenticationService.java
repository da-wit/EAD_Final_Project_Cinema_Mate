package com.cinemamate.cinema_mate.auth.services;

import com.cinemamate.cinema_mate.auth.dto.AuthRequest;
import com.cinemamate.cinema_mate.auth.dto.AuthResponse;
import com.cinemamate.cinema_mate.cinema.dto.CreateCinemaDto;
import com.cinemamate.cinema_mate.user.dto.CreateUserDto;

public interface IAuthenticationService {
    AuthResponse userRegister(CreateUserDto createUserDto);
    AuthResponse cinemaRegister(CreateCinemaDto createCinemaDto);
    AuthResponse authenticate(AuthRequest request);
}
