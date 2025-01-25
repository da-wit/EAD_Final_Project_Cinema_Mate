package com.cinemamate.cinema_mate.core.service;

import org.springframework.stereotype.Component;


public interface ICinemaUserHelper {
    boolean isNameConflict(String name);
    long bookedSeats(String id);
}
