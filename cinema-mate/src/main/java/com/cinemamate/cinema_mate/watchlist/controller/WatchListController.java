package com.cinemamate.cinema_mate.watchlist.controller;

import com.cinemamate.cinema_mate.watchlist.dto.WatchListDto;
import com.cinemamate.cinema_mate.watchlist.services.IWatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlist")
@RequiredArgsConstructor
public class WatchListController {

    private final IWatchListService watchListService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<WatchListDto>> getUserWatchList(Principal principal){
        String userName = principal.getName();

        return ResponseEntity.ok(watchListService.getUserWatchList(userName));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}")
    public ResponseEntity<WatchListDto> addToWatchList(@PathVariable("id") String movieId, Principal principal){
        String userName = principal.getName();
        return new ResponseEntity<>(watchListService.addToWatchList(userName,movieId), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> addToWatchList(@PathVariable("id") String watchListId){
        return ResponseEntity.ok(watchListService.removeFromWatchList(watchListId));
    }
}
