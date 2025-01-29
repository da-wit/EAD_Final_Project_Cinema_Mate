package com.cinemamate.cinema_mate.movie.mapper;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.mapper.CinemaMapper;
import com.cinemamate.cinema_mate.core.service.ICinemaUserHelper;
import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieMapper {
    private final ICinemaUserHelper cinemaUserHelper;
    private final CinemaMapper cinemaMapper;

    public MovieDto movieToMovieDto(Movie movie){
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .viewTime(movie.getViewTime())
                .viewDate(movie.getViewDate())
                .seats(movie.getSeats())
                .price(movie.getPrice())
                .genres(movie.getGenres())
                .imagePath(movie.getImagePath())
                .isActive(movie.isActive())
                .build();
    }

    public MovieDetailDto movieToMovieDetailDto(Movie movie) {
        return movieToMovieDetailDto(movie, false,null); // Call the main method with Optional.empty()
    }

    public  MovieDetailDto movieToMovieDetailDto(Movie movie, boolean alreadyBooked,String alreadyInTheWatchList){
        BasicCinemaDto basicCinemaDto = cinemaMapper.cinemaToBasicCinemaDto(movie.getCinema());
        long bookedSeats = cinemaUserHelper.bookedSeats(movie.getId());
        return MovieDetailDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .viewTime(movie.getViewTime())
                .viewDate(movie.getViewDate())
                .seats(movie.getSeats())
                .bookedSeats(bookedSeats)
                .price(movie.getPrice())
                .genres(movie.getGenres())
                .imagePath(movie.getImagePath())
                .isActive(movie.isActive())
                .alreadyBooked(alreadyBooked)
                .alreadyInTheWatchList(alreadyInTheWatchList)
                .cinema(basicCinemaDto)
                .build();
    }
}
