package com.cinemamate.cinema_mate.watchlist.repository;

import com.cinemamate.cinema_mate.movie.entity.Movie;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.watchlist.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WatchListRepository extends JpaRepository<WatchList, UUID> {
    List<WatchList> findAllWatchListByUser(User user);
    Optional<WatchList> findWatchListByUserAndMovie(User user, Movie movie);
    Optional<WatchList> findById(String id);

    boolean existsWatchListByMovie_IdAndUser_Id(String movieId, String userId);

    Optional<WatchList> findWatchListByMovie_IdAndUser_Id(String movieId, String userId);
}
