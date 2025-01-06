package com.cinemamate.cinema_mate.movie.mapper;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.mapper.CinemaMapper;
import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import com.cinemamate.cinema_mate.movie.entity.Movie;

public class MovieMapper {
    public static MovieDto movieToMovieDto(Movie movie){
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .viewDate(movie.getViewDate())
                .seats(movie.getSeats())
                .imagePath(movie.getImagePath())
                .build();
    }

    public static MovieDetailDto movieToMovieDetailDto(Movie movie){
        BasicCinemaDto basicCinemaDto = CinemaMapper.cinemaToBasicCinemaDto(movie.getCinema());
        return MovieDetailDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .viewDate(movie.getViewDate())
                .seats(movie.getSeats())
                .imagePath(movie.getImagePath())
                .cinema(basicCinemaDto)
                .build();
    }
}
