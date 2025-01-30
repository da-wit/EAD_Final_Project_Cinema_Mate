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
    List<MovieDto> getAllMovies(String search);
    List<MovieDto> getAllDatePassedMovies(String cinemaName,String search);
    MovieDetailDto getMovieById(String movieId,String userName);
    MovieDetailDto getMovieByIdForCinema(String movieId,String cinemaName);
    List<MovieDto> getMoviesByCinemaId(String cinemaId,String search);
    // this returns the movies of the currently logged in cinema
    List<MovieDto> getMoviesByCinema(String cinemaName,String search);

    long getTotalMovieSeats(String movieId);
}
