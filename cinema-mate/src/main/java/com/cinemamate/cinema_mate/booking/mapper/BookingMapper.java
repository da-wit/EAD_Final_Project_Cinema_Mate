package com.cinemamate.cinema_mate.booking.mapper;


import com.cinemamate.cinema_mate.booking.dto.BookingCinemaDto;
import com.cinemamate.cinema_mate.booking.dto.BookingDto;
import com.cinemamate.cinema_mate.booking.entity.Booking;
import com.cinemamate.cinema_mate.movie.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final MovieMapper MovieMapper;
    public  BookingDto bookingToBookingDto(Booking booking){
        return BookingDto.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .numberOfSeats(booking.getNumberOfSeats())
                .totalPrice(BigDecimal.valueOf(booking.getNumberOfSeats()).multiply(booking.getMovie().getPrice()))
                .bookedAt(booking.getBookedAt())
                .movieDetailDto(MovieMapper.movieToMovieDetailDto(booking.getMovie()))
                .build();
    }

    public  BookingCinemaDto bookingToBookingCinemaDto(Booking booking){
        return BookingCinemaDto.builder()
                .id(booking.getId())
                .userName(booking.getUser().getUsername())
                .imagePath(booking.getUser().getProfileImage())
                .bookedSeats(booking.getNumberOfSeats())
                .totalPrice(BigDecimal.valueOf(booking.getNumberOfSeats()).multiply(booking.getMovie().getPrice()))
                .movieDto(MovieMapper.movieToMovieDto(booking.getMovie()))
                .bookedAt(booking.getBookedAt())
                .build();
    }
}
