package com.cinemamate.cinema_mate.movie.services;

import com.cinemamate.cinema_mate.movie.dto.CreateMovieDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;

public interface IMovieService {
    MovieDto createMovie(CreateMovieDto createMovieDto);
}
