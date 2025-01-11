package com.cinemamate.cinema_mate.booking.mapper;


import com.cinemamate.cinema_mate.booking.dto.BookingCinemaDto;
import com.cinemamate.cinema_mate.booking.dto.BookingDto;
import com.cinemamate.cinema_mate.booking.entity.Booking;
import com.cinemamate.cinema_mate.movie.mapper.MovieMapper;

public class BookingMapper {
    public static BookingDto bookingToBookingDto(Booking booking){
        return BookingDto.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .numberOfSeats(booking.getNumberOfSeats())
                .bookedAt(booking.getBookedAt())
                .movieDetailDto(MovieMapper.movieToMovieDetailDto(booking.getMovie()))
                .build();
    }

    public static BookingCinemaDto bookingToBookingCinemaDto(Booking booking){
        return BookingCinemaDto.builder()
                .id(booking.getId())
                .userName(booking.getUser().getUsername())
                .movieDto(MovieMapper.movieToMovieDto(booking.getMovie()))
                .bookedAt(booking.getBookedAt())
                .build();
    }
}
