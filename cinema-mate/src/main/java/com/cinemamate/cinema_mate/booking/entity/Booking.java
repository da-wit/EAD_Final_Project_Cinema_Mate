package com.cinemamate.cinema_mate.booking.entity;

import com.cinemamate.cinema_mate.core.base.AuditableEntity;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import com.cinemamate.cinema_mate.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "numberOfSeats")
    @Setter
    private long numberOfSeats;

    @Column(name = "bookingCode")
    @Setter
    private String bookingCode;

    @Column(name = "bookedAt")
    @Setter
    private LocalTime bookedAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;


}
