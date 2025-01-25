package com.cinemamate.cinema_mate.movie.services;

import com.cinemamate.cinema_mate.movie.dto.CreateMovieDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import com.cinemamate.cinema_mate.movie.dto.UpdateMovieDto;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMovieService {
    Movie getMovie(String movieId);
    MovieDto createMovie(CreateMovieDto createMovieDto, MultipartFile imageFile,String cinemaName);
    MovieDto updateMovie(UpdateMovieDto updateMovieDto, MultipartFile imageFile, String movieId, String cinemaName);
    String deleteMovieById(String movieId,String cinemaName);
    List<MovieDto> getAllMovies();
    List<MovieDto> getAllDatePassedMovies();
    MovieDetailDto getMovieById(String movieId);
    List<MovieDto> getMoviesByCinemaId(String cinemaId);
    // this returns the movies of the currently logged in cinema
    List<MovieDto> getMoviesByCinema(String cinemaName);

    long getTotalMovieSeats(String movieId);
}
