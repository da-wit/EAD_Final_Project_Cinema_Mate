package com.cinemamate.cinema_mate.watchlist.mapper;

import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import com.cinemamate.cinema_mate.movie.mapper.MovieMapper;
import com.cinemamate.cinema_mate.watchlist.dto.WatchListDto;
import com.cinemamate.cinema_mate.watchlist.entity.WatchList;

public class WatchListMapper {
    public static WatchListDto watchListToWatchListDto(WatchList watchList){
        MovieDetailDto movieDetailDto = MovieMapper.movieToMovieDetailDto(watchList.getMovie());
        return  WatchListDto.builder()
                .id(watchList.getId())
                .movieDetailDto(movieDetailDto)
                .build();
    }
}
