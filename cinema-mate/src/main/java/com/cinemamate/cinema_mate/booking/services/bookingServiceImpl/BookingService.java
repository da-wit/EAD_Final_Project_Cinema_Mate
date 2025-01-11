package com.cinemamate.cinema_mate.booking.services.bookingServiceImpl;

import com.cinemamate.cinema_mate.booking.dto.BookingCinemaDto;
import com.cinemamate.cinema_mate.booking.dto.BookingDto;
import com.cinemamate.cinema_mate.booking.entity.Booking;
import com.cinemamate.cinema_mate.booking.exceptions.BookingExceptions;
import com.cinemamate.cinema_mate.booking.mapper.BookingMapper;
import com.cinemamate.cinema_mate.booking.repository.BookingRepository;
import com.cinemamate.cinema_mate.booking.services.IBookingService;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.core.util.BookingCodeGenerator;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import com.cinemamate.cinema_mate.movie.exceptions.MovieExceptions;
import com.cinemamate.cinema_mate.movie.services.IMovieService;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import com.cinemamate.cinema_mate.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final IMovieService movieService;
    private final IUserService userService;
    private final ICinemaService cinemaService;
    private final BookingRepository bookingRepository;

    @Override
    public String bookAMovie(String userName, String movieId, Long numberOfSeats) {
        User user = userService.getUser(userName);
        if (user == null) {
            throw UserExceptions.usernameNotFound(userName);
        }
        Movie movie = movieService.getMovie(movieId);
        if (movie == null) {
            throw MovieExceptions.movieNotFound(movieId);
        }
        if ( movie.getSeats() - numberOfBookedSeats(movieId) == 0) {
            throw BookingExceptions.noAvailableSeats();
        }
        else if( movie.getSeats() - numberOfBookedSeats(movieId)  < numberOfSeats){
            throw BookingExceptions.insufficientSeatsAvailable();
        }

        String code = BookingCodeGenerator.generateBookingCode();
        Booking booking = Booking.builder()
                .numberOfSeats(numberOfSeats)
                .bookingCode(code)
                .bookedAt(LocalTime.now())
                .user(user)
                .movie(movie)
                .build();

        bookingRepository.save(booking);
        return "Movie Booked successfully";
    }

    @Override
    public String updateBookedNumberOfSeats(String userName, String bookingId, Long numberOfSeats) {
        User user = userService.getUser(userName);
        if (user == null) {
            throw UserExceptions.usernameNotFound(userName);
        }
        Booking booking = bookingRepository.findBookingById(bookingId).orElseThrow(() -> BookingExceptions.notFound(bookingId));
        Movie movie = booking.getMovie();
        if ( movie.getSeats() - numberOfBookedSeats(movie.getId()) == 0) {
            throw BookingExceptions.noAvailableSeats();
        }
        else if( movie.getSeats() - numberOfBookedSeats(movie.getId())  < numberOfSeats){
            throw BookingExceptions.insufficientSeatsAvailable();
        }
        Long currentUserBookedSeats = booking.getNumberOfSeats();

        if (numberOfSeats ==0){
            bookingRepository.delete(booking);
            return "Booked movie removed successfully";
        }
        booking.setNumberOfSeats(numberOfSeats);
        bookingRepository.save(booking);
        return "number of booked seats updated successfully";
    }

    @Override
    public String removeBookedMovie(String userName, String bookingId) {
        User user = userService.getUser(userName);
        if (user == null) {
            throw UserExceptions.usernameNotFound(userName);
        }
        Booking booking = bookingRepository.findBookingById(bookingId).orElseThrow(() -> BookingExceptions.notFound(bookingId));

        bookingRepository.delete(booking);
        return "Booked movie removed successfully";
    }

    @Override
    public List<BookingDto> getUserBookings(String userName) {
        User user = userService.getUser(userName);
        if (user == null) {
            throw UserExceptions.usernameNotFound(userName);
        }
        List<Booking> bookings = bookingRepository.findAllByUser(user).orElse(new ArrayList<>());

        return  bookings.stream().map(BookingMapper::bookingToBookingDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingCinemaDto> getCinemaBookings(String cinemaName) {
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if (cinema == null){
            throw CinemaExceptions.cinemaNameNotFound(cinemaName);
        }
        List<Booking> bookings = bookingRepository.findAllByMovie_Cinema_id(cinema.getId()).orElse(new ArrayList<>());
        return bookings.stream().map(BookingMapper::bookingToBookingCinemaDto).collect(Collectors.toList());
    }

    @Override
    public long numberOfBookedSeats(String movieId) {
        Long totalSeats = movieService.getTotalMovieSeats(movieId);
        List<Booking> bookings = bookingRepository.findAllByMovie_Id(movieId).orElse(null);
        if (bookings == null){
            return 0;
        }
        return bookings.stream().mapToLong(Booking::getNumberOfSeats).sum();
    }


}
