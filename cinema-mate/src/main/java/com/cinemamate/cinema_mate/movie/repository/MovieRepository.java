package com.cinemamate.cinema_mate.movie.repository;

import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> findMovieByTitle(String title);
    Optional<Movie> findAllByTitle(String title);
    Optional<Movie> findMovieById(String id);
    List<Movie> findMoviesByCinema(Cinema cinema);

    List<Movie> findByTitleContainingIgnoreCase(String title, Sort sort);

    List<Movie> findAllByIsActiveTrueAndViewDateBefore(LocalDate viewDateBefore);

}
