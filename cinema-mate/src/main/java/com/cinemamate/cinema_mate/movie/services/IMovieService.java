package com.cinemamate.cinema_mate.movie.services;

import com.cinemamate.cinema_mate.movie.dto.CreateMovieDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

public interface IMovieService {
    MovieDto createMovie(CreateMovieDto createMovieDto, MultipartFile imageFile);
    MovieDto updateMovie(CreateMovieDto createMovieDto,MultipartFile imageFile,String id);
}
