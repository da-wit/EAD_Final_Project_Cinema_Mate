package com.cinemamate.cinema_mate.watchlist.services;

import com.cinemamate.cinema_mate.watchlist.dto.WatchListDto;

import java.util.List;

public interface IWatchListService {
    WatchListDto addToWatchList(String userName,String movieId);
    String removeFromWatchList(String watchListId);
    List<WatchListDto> getUserWatchList(String userName);
}
