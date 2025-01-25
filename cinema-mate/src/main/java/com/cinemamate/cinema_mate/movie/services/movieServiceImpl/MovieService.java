package com.cinemamate.cinema_mate.movie.services.movieServiceImpl;

import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.core.service.ICinemaUserHelper;
import com.cinemamate.cinema_mate.core.service.IFileService;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final IFileService fileService;
    private final ICinemaService cinemaService;
    private final ICinemaUserHelper cinemaUserHelper;
    private final MovieMapper movieMapper;

    @Override
    public Movie getMovie(String movieId) {
        return movieRepository.findMovieById(movieId).orElseThrow(() -> MovieExceptions.movieNotFound(movieId));
    }

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
                .viewTime(createMovieDto.getViewTime())
                .viewDate(createMovieDto.getViewDate())
                .seats(createMovieDto.getSeats())
                .price(createMovieDto.getPrice())
                .genres(createMovieDto.getGenres())
                .imagePath(savedImagePath)
                .cinema(cinema)
                .build();
        movieRepository.save(newMovie);


        return movieMapper.movieToMovieDto(newMovie);
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
        movie.setViewTime(updateMovieDto.getViewTime());
        movie.setViewDate(updateMovieDto.getViewDate());
        movie.setSeats(updateMovieDto.getSeats());
        movie.setImagePath(savedImagePath);

        movieRepository.save(movie);

        return movieMapper.movieToMovieDto(movie);
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

    // view get all movies that are active so the user can access them
    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
        return movies.stream()
                .filter(Movie::isActive)
                .map(movieMapper::movieToMovieDto).collect(Collectors.toList());
//        return movies.stream().map(movie -> movieMapper.movieToMovieDto(movie)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getAllDatePassedMovies() {
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
        return movies.stream().filter(movie -> !movie.isActive()).map(movieMapper::movieToMovieDto).collect(Collectors.toList());
    }


    @Override
    public MovieDetailDto getMovieById(String movieId) {
        Movie movie = movieRepository.findMovieById(movieId).orElseThrow(() -> MovieExceptions.movieNotFound(movieId));
        long numberOfBookedSeats = cinemaUserHelper.bookedSeats(movie.getId());
        return movieMapper.movieToMovieDetailDto(movie);
    }

    @Override
    public List<MovieDto> getMoviesByCinemaId(String cinemaId) {
        Cinema cinema = cinemaService.getCinemaById(cinemaId);
        List<Movie> movies = movieRepository.findMoviesByCinema(cinema);

        return movies.stream().map(movieMapper::movieToMovieDto).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getMoviesByCinema(String cinemaName) {
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if(cinema == null){
            throw CinemaExceptions.cinemaNameNotFound(cinemaName);
        }
        List<Movie> movies = movieRepository.findMoviesByCinema(cinema);
        return movies.stream().map(movieMapper::movieToMovieDto).collect(Collectors.toList());
    }

    @Override
    public long getTotalMovieSeats(String movieId) {
        Movie movie = movieRepository.findMovieById(movieId).orElseThrow(() -> MovieExceptions.movieNotFound(movieId));
        return movie.getSeats();
    }


    @Scheduled(cron = "0 0 0 * * *") // Runs daily at midnight
//    @Scheduled(cron = "0 0/2 * * * *")
    public void deactivatePastMovies() {
        LocalDate today = LocalDate.now();

        List<Movie> moviesToDeactivate = movieRepository.findAllByIsActiveTrueAndViewDateBefore(today);
        moviesToDeactivate.forEach(movie -> {
            movie.setActive(false);
            System.out.println("Deactivated movie: " + movie.getTitle() + " (ID: " + movie.getId() + ")");
        });

        movieRepository.saveAll(moviesToDeactivate);
    }

}
