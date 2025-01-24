package com.cinemamate.cinema_mate.cinema.repository;

import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CinemaRepository extends JpaRepository<Cinema, UUID> {
    Optional<Cinema> findCinemaByCinemaName(String cinemaName);
    Optional<Cinema> findCinemaById(String id);;

    boolean existsByCinemaName(String cinemaName);

    boolean existsByEmail(String email);

}
