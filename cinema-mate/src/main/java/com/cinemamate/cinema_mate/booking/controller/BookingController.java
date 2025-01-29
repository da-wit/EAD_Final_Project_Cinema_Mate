package com.cinemamate.cinema_mate.booking.controller;

import com.cinemamate.cinema_mate.booking.dto.BookingCinemaDto;
import com.cinemamate.cinema_mate.booking.dto.BookingDto;
import com.cinemamate.cinema_mate.booking.dto.BookingRequestDto;
import com.cinemamate.cinema_mate.booking.dto.VerificationDto;
import com.cinemamate.cinema_mate.booking.services.IBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private final IBookingService bookingService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public ResponseEntity<List<BookingDto>> getUserBookings(Principal principal){
        String userName = principal.getName();
        return ResponseEntity.ok(bookingService.getUserBookings(userName));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @GetMapping("/cinema")
    public ResponseEntity<List<BookingCinemaDto>> getCinemaBookings(Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(bookingService.getCinemaBookings(cinemaName));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @PostMapping("/verify")
    public ResponseEntity<BookingCinemaDto> verify(@Valid @RequestBody VerificationDto verificationDto,Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(bookingService.verifyCode(verificationDto,cinemaName));
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}")
    public ResponseEntity<String> bookAMovie(@PathVariable("id") String movieId, Principal principal, @Valid  @RequestBody BookingRequestDto bookingRequestDto){
        System.out.println(bookingRequestDto.getSeats());
        String userName = principal.getName();
        return new ResponseEntity<>(bookingService.bookAMovie(userName,movieId,bookingRequestDto.getSeats()), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable("id") String bookingId,Principal principal,@Valid  @RequestBody BookingRequestDto bookingRequestDto){
        String userName = principal.getName();
        return  ResponseEntity.ok(bookingService.updateBookedNumberOfSeats(userName,bookingId,bookingRequestDto.getSeats()));
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable("id") String bookingId,Principal principal){
        String userName = principal.getName();
        return  ResponseEntity.ok(bookingService.removeBookedMovie(userName,bookingId));
    }

}
