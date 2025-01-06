package com.cinemamate.cinema_mate.movie.services.movieServiceImpl;

import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.cinema.services.cinemaServiceImpl.CinemaService;
import com.cinemamate.cinema_mate.core.service.FileService;
import com.cinemamate.cinema_mate.movie.dto.CreateMovieDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import com.cinemamate.cinema_mate.movie.dto.UpdateMovieDto;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import com.cinemamate.cinema_mate.movie.exceptions.MovieExceptions;
import com.cinemamate.cinema_mate.movie.mapper.MovieMapper;
import com.cinemamate.cinema_mate.movie.repository.MovieRepository;
import com.cinemamate.cinema_mate.movie.services.IMovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final FileService fileService;
    private final CinemaService cinemaService;

    @Override
    public MovieDto createMovie(CreateMovieDto createMovieDto, MultipartFile imageFile,String cinemaName) {
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if (cinema == null){
            throw CinemaExceptions.cinemaNameNotFound(cinemaName);
        }
        String savedImagePath = fileService.saveImage(imageFile);

        Movie newMovie = Movie.builder()
                .title(createMovieDto.getTitle())
                .description(createMovieDto.getDescription())
                .duration(createMovieDto.getDuration())
                .viewDate(createMovieDto.getViewDate())
                .seats(createMovieDto.getSeats())
                .imagePath(savedImagePath)
                .cinema(cinema)
                .build();
        movieRepository.save(newMovie);


        return MovieMapper.movieToMovieDto(newMovie);
    }

    @Override
    public MovieDto updateMovie(UpdateMovieDto updateMovieDto, MultipartFile imageFile, String movieId, String cinemaName) {
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if (cinema == null){
            throw  CinemaExceptions.cinemaNameNotFound(cinemaName);
        }
        System.out.println("inside update movie");
        System.out.println(movieId);
        System.out.println(UUID.fromString(movieId));
        Movie movie = movieRepository.findMovieById(movieId).orElseThrow(()-> MovieExceptions.movieNotFound(movieId));
        fileService.deleteImage(movie.getImagePath());
        String savedImagePath = fileService.saveImage(imageFile);
        movie.setTitle(updateMovieDto.getTitle());
        movie.setDescription(updateMovieDto.getDescription());
        movie.setDuration(updateMovieDto.getDuration());
        movie.setViewDate(updateMovieDto.getViewDate());
        movie.setSeats(updateMovieDto.getSeats());
        movie.setImagePath(savedImagePath);
        return MovieMapper.movieToMovieDto(movie);
    }

    @Override
    public String deleteMovieById(String movieId, String cinemaName) {
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if (cinema == null){
            throw  CinemaExceptions.notFound(movieId);
        }
        Movie movie = movieRepository.findMovieById(movieId).orElseThrow(() -> MovieExceptions.movieNotFound(movieId));
        movieRepository.delete(movie);
        fileService.deleteImage(movie.getImagePath());
        return "Movie deleted successfully";
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
        return movies.stream().map(MovieMapper::movieToMovieDto).collect(Collectors.toList());
//        return movies.stream().map(movie -> MovieMapper.movieToMovieDto(movie)).collect(Collectors.toList());
    }

    @Override
    public MovieDetailDto getMovieById(String movieId) {
        Movie movie = movieRepository.findMovieById(movieId).orElseThrow(() -> MovieExceptions.movieNotFound(movieId));
        return MovieMapper.movieToMovieDetailDto(movie);
    }

    @Override
    public List<MovieDto> getMoviesByCinemaId(String cinemaId) {
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        if(cinema == null){
            throw CinemaExceptions.notFound(cinemaId);
        }
        List<Movie> movies = movieRepository.findMoviesByCinema(cinema);

        return movies.stream().map(MovieMapper::movieToMovieDto).collect(Collectors.toList());
    }


}
