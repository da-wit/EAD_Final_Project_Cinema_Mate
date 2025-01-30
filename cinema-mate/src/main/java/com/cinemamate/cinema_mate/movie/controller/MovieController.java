package com.cinemamate.cinema_mate.movie.controller;

import com.cinemamate.cinema_mate.movie.dto.CreateMovieDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDetailDto;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import com.cinemamate.cinema_mate.movie.dto.UpdateMovieDto;
import com.cinemamate.cinema_mate.movie.services.movieServiceImpl.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies(@RequestParam(value = "search", required = false) String search)
    {
        return ResponseEntity.ok(movieService.getAllMovies(search));
    }
    @PreAuthorize("hasAuthority('CINEMA')")
    @GetMapping("/datePassed")
    public ResponseEntity<List<MovieDto>> getAllDatePassedMovies(Principal principal,@RequestParam(value = "search", required = false) String search){
        return ResponseEntity.ok(movieService.getAllDatePassedMovies(principal.getName(),search));
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDto> getMovieById(@PathVariable("id") String movieId,Principal principal){
        return ResponseEntity.ok(movieService.getMovieById(movieId,principal.getName()));
    }

    @GetMapping("/detail/cinema/{id}")
    public ResponseEntity<MovieDetailDto> getMovieByIdForCinema(@PathVariable("id") String movieId,Principal principal){
        return ResponseEntity.ok(movieService.getMovieByIdForCinema(movieId,principal.getName()));
    }

    @GetMapping("/cinema/{id}")
    public  ResponseEntity<List<MovieDto>> getMoviesByCinemaId(@PathVariable("id") String cinemaId,@RequestParam(value = "search", required = false) String search){
        return ResponseEntity.ok(movieService.getMoviesByCinemaId(cinemaId,search));
    }
    @PreAuthorize("hasAuthority('CINEMA')")
    @GetMapping("/cinema")
    public  ResponseEntity<List<MovieDto>> getMoviesByCinema(Principal principal,@RequestParam(value = "search", required = false) String search){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(movieService.getMoviesByCinema(cinemaName,search));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(
            @RequestPart("createMovieDto") @Valid CreateMovieDto createMovieDto,
            @RequestPart("image") MultipartFile imageFile, Principal principal) {
            String cinemaName = principal.getName();
        return new ResponseEntity<>(movieService.createMovie(createMovieDto, imageFile, cinemaName), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie (@RequestPart("updateMovieDto") @Valid UpdateMovieDto updateMovieDto, @RequestPart(value = "image", required = false) MultipartFile imageFile,Principal principal,@PathVariable("id") String movieId){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(movieService.updateMovie(updateMovieDto,imageFile,movieId,cinemaName));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") String movieId,Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(movieService.deleteMovieById(movieId,cinemaName));
    }







}
