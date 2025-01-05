package com.cinemamate.cinema_mate.movie.services.movieServiceImpl;

import com.cinemamate.cinema_mate.movie.dto.CreateMovieDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import com.cinemamate.cinema_mate.movie.exceptions.MovieExceptions;
import com.cinemamate.cinema_mate.movie.mapper.MovieMapper;
import com.cinemamate.cinema_mate.movie.repository.MovieRepository;
import com.cinemamate.cinema_mate.movie.services.IMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieDto createMovie(CreateMovieDto createMovieDto) {
//        String title = createMovieDto.getTitle();
//        String description;
//        LocalTime duration;
//        LocalDate viewDate;
//        Long seats;
//        String imagePath;
        Movie movie = movieRepository.findMovieByTitle(createMovieDto.getTitle()).orElse(null);
        if (movie != null) {

            boolean movieExists = movie.getTitle().equals(createMovieDto.getTitle()) &&
                    movie.getDescription().equals(createMovieDto.getDescription()) &&
                    movie.getDuration().equals(createMovieDto.getDuration()) &&
                    movie.getViewDate().equals(createMovieDto.getViewDate()) &&
                    movie.getImagePath().equals(createMovieDto.getImagePath()) &&
                    movie.getSeats().equals(createMovieDto.getSeats());
            if (movieExists) {
                throw MovieExceptions.movieAlreadyExist(createMovieDto.getTitle());
            }

        }
        Movie newMovie = Movie.builder()
                .title(createMovieDto.getTitle())
                .description(createMovieDto.getDescription())
                .duration(createMovieDto.getDuration())
                .viewDate(createMovieDto.getViewDate())
                .seats(createMovieDto.getSeats())
                .imagePath(createMovieDto.getImagePath())
                .build();
        movieRepository.save(newMovie);

        return MovieMapper.movieToMovieDto(newMovie);
    }
}
