package com.cinemamate.cinema_mate.watchlist.mapper;

import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import com.cinemamate.cinema_mate.movie.mapper.MovieMapper;
import com.cinemamate.cinema_mate.watchlist.dto.WatchListDto;
import com.cinemamate.cinema_mate.watchlist.entity.WatchList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WatchListMapper {
    private final MovieMapper movieMapper;
    public  WatchListDto watchListToWatchListDto(WatchList watchList){
        MovieDetailDto movieDetailDto = movieMapper.movieToMovieDetailDto(watchList.getMovie());
        return  WatchListDto.builder()
                .id(watchList.getId())
                .movieDetailDto(movieDetailDto)
                .build();
    }
}
