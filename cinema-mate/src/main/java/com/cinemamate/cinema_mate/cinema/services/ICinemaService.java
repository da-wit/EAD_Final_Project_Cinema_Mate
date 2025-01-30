package com.cinemamate.cinema_mate.cinema.services;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdateCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdatePasswordDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICinemaService {
    CinemaDto getCinemaByCinemaName(String cinemaName);
    boolean cinemaExists(String cinemaName);
    Cinema getCinema(String cinemaName);
    CinemaDto saveCinema(Cinema cinema);
    Cinema getCinemaById(String id);

    CinemaDto updateCinema(String cinemaName, UpdateCinemaDto updateCinemaDto);
    CinemaDto getCinemaDetail(String cinemaName);
    String uploadCinemaProfile(String cinemaName, MultipartFile imageFile);
    String updatePassword(String cinemaName, UpdatePasswordDto updatePasswordDto);
    List<CinemaDto> getAllCinema(String search);
    BasicCinemaDto getCinemaDetailById(String id);

}
