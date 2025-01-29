package com.cinemamate.cinema_mate.core.service.cinemaUserHelperImpl;

import com.cinemamate.cinema_mate.booking.entity.Booking;
import com.cinemamate.cinema_mate.booking.repository.BookingRepository;
import com.cinemamate.cinema_mate.cinema.repository.CinemaRepository;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.core.service.ICinemaUserHelper;
import com.cinemamate.cinema_mate.movie.exceptions.MovieExceptions;
import com.cinemamate.cinema_mate.movie.repository.MovieRepository;
import com.cinemamate.cinema_mate.user.repository.UserRepository;
import com.cinemamate.cinema_mate.user.services.IUserService;
import com.cinemamate.cinema_mate.watchlist.entity.WatchList;
import com.cinemamate.cinema_mate.watchlist.repository.WatchListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaUserHelper implements ICinemaUserHelper {
    private final UserRepository userRepository;
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;
    private final BookingRepository bookingRepository;
    private final WatchListRepository watchListRepository;
    @Override
    public boolean isNameConflict(String name) {
        return cinemaRepository.existsByCinemaName(name) || userRepository.existsByUsername(name);
    }

    @Override
    public long bookedSeats(String id) {
        long totalSeats = movieRepository.findMovieById(id).orElseThrow(()-> MovieExceptions.movieNotFound(id)).getSeats();
        List<Booking> bookings = bookingRepository.findAllByMovie_Id(id).orElse(null);
        if (bookings == null){
            return 0;
        }
        return bookings.stream().mapToLong(Booking::getNumberOfSeats).sum();
    }

    @Override
    public boolean isBookedByUser(String movieId, String userId) {
        return bookingRepository.existsBookingByUser_IdAndMovie_Id(userId,movieId);
    }

    @Override
    public boolean isAlreadyInUsersWatchList(String movieId, String userId) {
        return watchListRepository.existsWatchListByMovie_IdAndUser_Id(movieId,userId);
    }

    @Override
    public String watchListId(String movieId, String userId) {
        WatchList watchList =watchListRepository.findWatchListByMovie_IdAndUser_Id(movieId,userId).orElse(null);
        if(watchList != null){
            return watchList.getId();
        }
        return null;
    }
}
