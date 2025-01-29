package com.cinemamate.cinema_mate.booking.repository;

import com.cinemamate.cinema_mate.booking.dto.BookingCinemaDto;
import com.cinemamate.cinema_mate.booking.entity.Booking;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import com.cinemamate.cinema_mate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<List<Booking>> findAllByMovie_Id(String movieId);

    Optional<Booking> findBookingById(String id);

    Optional<Booking> findByUserAndMovie(User user, Movie movie);
    Optional<List<Booking>> findAllByUser(User user);

    Optional<List<Booking>> findAllByMovie_Cinema_id(String movieCinemaId);

    Optional<Booking> findBookingByUserAndBookingCode(User user, String bookingCode);

    Optional<Booking> findBookingByBookingCode(String bookingCode);

    Optional<Booking> findBookingByMovie_IdAndUser_Id(String movieId, String userId);

    boolean existsBookingByUser_IdAndMovie_Id(String userId, String movieId);
}
