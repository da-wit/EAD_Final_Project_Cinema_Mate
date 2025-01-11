package com.cinemamate.cinema_mate.booking.services;

import com.cinemamate.cinema_mate.booking.dto.BookingCinemaDto;
import com.cinemamate.cinema_mate.booking.dto.BookingDto;

import java.util.List;

public interface IBookingService {
    // add movie to the booking
    String bookAMovie(String userName,String movieId,Long numberOfSeats);
    // update / remove booking
    String updateBookedNumberOfSeats(String userName,String bookingId,Long numberOfSeats);
    // remove booking
    String removeBookedMovie(String userName,String bookingId);

    // getAll user Booked movies
    List<BookingDto> getUserBookings(String userName);
    List<BookingCinemaDto> getCinemaBookings(String cinemaName);


    long numberOfBookedSeats(String movieId);
}
