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
    public ResponseEntity<List<MovieDto>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDto> getMovieById(@PathVariable("id") String movieId){
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }
    @GetMapping("/cinema/{id}")
    public  ResponseEntity<List<MovieDto>> getMoviesByCinemaId(@PathVariable("id") String cinemaId){
        return ResponseEntity.ok(movieService.getMoviesByCinemaId(cinemaId));
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
    public ResponseEntity<MovieDto> updateMovie (@RequestPart("updateMovieDto") @Valid UpdateMovieDto updateMovieDto, @RequestPart("image") MultipartFile imageFile,Principal principal,@PathVariable("id") String movieId){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(movieService.updateMovie(updateMovieDto,imageFile,movieId,cinemaName));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") String movieId,Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(movieService.deleteMovieById(movieId,cinemaName));
    }




//@PostMapping
//public ResponseEntity<String> createMovie(
//        @RequestPart("createMovieDto")  String createMovieDto,
//        @RequestPart("image") MultipartFile imageFile){
//    System.out.println(createMovieDto);
//    return new ResponseEntity<>(createMovieDto, HttpStatus.CREATED);
//}
//    @GetMapping
//    public ResponseEntity<String> getMovie(
//           ){
//        return new ResponseEntity<>("lldlfdf", HttpStatus.CREATED);
//    }

//    @PreAuthorize("hasAuthority('CINEMA')")
//    @GetMapping
//    public ResponseEntity<String> b (Principal principal){
//        System.out.println("bbbb");
//        System.out.println(principal);
//        return ResponseEntity.ok(principal.getName().toString());
//    }


}
