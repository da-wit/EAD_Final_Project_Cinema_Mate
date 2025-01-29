package com.cinemamate.cinema_mate.core.service;

import org.springframework.stereotype.Component;


public interface ICinemaUserHelper {
    boolean isNameConflict(String name);
    long bookedSeats(String id);
    boolean isBookedByUser(String movieId,String userId);
    boolean isAlreadyInUsersWatchList(String movieId,String userId);
    String watchListId(String movieId,String userId);
}
