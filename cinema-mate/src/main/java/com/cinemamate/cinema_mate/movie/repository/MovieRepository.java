package com.cinemamate.cinema_mate.movie.repository;

import com.cinemamate.cinema_mate.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> findMovieByTitle(String title);
    Optional<Movie> findAllByTitle(String title);
}
