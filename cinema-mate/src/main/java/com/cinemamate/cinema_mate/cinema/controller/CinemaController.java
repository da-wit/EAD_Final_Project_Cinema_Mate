package com.cinemamate.cinema_mate.cinema.controller;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdateCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdatePasswordDto;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.movie.dto.MovieDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/cinema")
@RequiredArgsConstructor
public class CinemaController {
    private final ICinemaService cinemaService;



    @GetMapping()
    public ResponseEntity<CinemaDto> getCinemaDetail(Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(cinemaService.getCinemaDetail(cinemaName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicCinemaDto> getCinemaDetailById(@PathVariable ("id") String cinemaId){

        return ResponseEntity.ok(cinemaService.getCinemaDetailById(cinemaId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CinemaDto>> getAllCinemas(@RequestParam(value = "search", required = false) String search){
        return ResponseEntity.ok(cinemaService.getAllCinema(search));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @PutMapping()
    public ResponseEntity<CinemaDto> updateCinemaInfo(@Valid @RequestBody UpdateCinemaDto updateCinemaDto, Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(cinemaService.updateCinema(cinemaName,updateCinemaDto));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @PutMapping("/profile")
    public  ResponseEntity<String> uploadProfileImage(MultipartFile imageFile,Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(cinemaService.uploadCinemaProfile(cinemaName,imageFile));
    }

    @PreAuthorize("hasAuthority('CINEMA')")
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto,Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(cinemaService.updatePassword(cinemaName,updatePasswordDto));
    }
}
