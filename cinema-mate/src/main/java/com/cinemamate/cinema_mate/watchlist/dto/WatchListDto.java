package com.cinemamate.cinema_mate.watchlist.dto;

import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WatchListDto {
    private String id;
    private MovieDetailDto movieDetailDto;
}
