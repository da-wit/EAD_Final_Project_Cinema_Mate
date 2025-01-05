package com.cinemamate.cinema_mate.movie.controller;

import com.cinemamate.cinema_mate.movie.dto.CreateMovieDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import com.cinemamate.cinema_mate.movie.services.movieServiceImpl.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(
            @RequestPart("createMovieDto") @Valid CreateMovieDto createMovieDto,
            @RequestPart("image") MultipartFile imageFile){
        System.out.println(createMovieDto.getTitle());
        return new ResponseEntity<>(movieService.createMovie(createMovieDto,imageFile), HttpStatus.CREATED);
    }
//@PostMapping
//public ResponseEntity<String> createMovie(
//        @RequestPart("createMovieDto")  String createMovieDto,
//        @RequestPart("image") MultipartFile imageFile){
//    System.out.println(createMovieDto);
//    return new ResponseEntity<>(createMovieDto, HttpStatus.CREATED);
//}
    @GetMapping
    public ResponseEntity<String> getMovie(
           ){
        return new ResponseEntity<>("lldlfdf", HttpStatus.CREATED);
    }

}
