package com.cinemamate.cinema_mate.watchlist.services.watchListServiceImpl;

import com.cinemamate.cinema_mate.movie.entity.Movie;
import com.cinemamate.cinema_mate.movie.services.IMovieService;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import com.cinemamate.cinema_mate.user.services.IUserService;
import com.cinemamate.cinema_mate.watchlist.dto.WatchListDto;
import com.cinemamate.cinema_mate.watchlist.entity.WatchList;
import com.cinemamate.cinema_mate.watchlist.exceptions.WatchListExceptions;
import com.cinemamate.cinema_mate.watchlist.mapper.WatchListMapper;
import com.cinemamate.cinema_mate.watchlist.repository.WatchListRepository;
import com.cinemamate.cinema_mate.watchlist.services.IWatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchListService implements IWatchListService {

    private final WatchListRepository watchListRepository;
    private final IUserService userService;
    private final IMovieService movieService;
    private final WatchListMapper watchListMapper;

    @Override
    public WatchListDto addToWatchList(String userName, String movieId) {
        User user = userService.getUser(userName);
        if(user == null){
            throw UserExceptions.usernameNotFound(userName);
        }
        Movie movie = movieService.getMovie(movieId);

        WatchList existingWatchList = watchListRepository.findWatchListByUserAndMovie(user,movie).orElse(null);
        if (existingWatchList != null){
            throw WatchListExceptions.alreadyInTheWatchList();
        }

        WatchList watchList = WatchList.builder()
                .user(user)
                .movie(movie)
                .build();
        watchListRepository.save(watchList);
        return watchListMapper.watchListToWatchListDto(watchList);
    }

    @Override
    public String removeFromWatchList(String watchListId) {
        WatchList watchList = watchListRepository.findById(watchListId).orElseThrow(()-> WatchListExceptions.watchListNotFound(watchListId));
        watchListRepository.delete(watchList);
        return "watchList deleted successfully";
    }


    @Override
    public List<WatchListDto> getUserWatchList(String userName) {
        User user = userService.getUser(userName);
        if(user == null){
            throw UserExceptions.usernameNotFound(userName);
        }
        List<WatchList> watchLists = watchListRepository.findAllWatchListByUser(user);
        return watchLists.stream().map(watchListMapper::watchListToWatchListDto).collect(Collectors.toList());
    }
}
