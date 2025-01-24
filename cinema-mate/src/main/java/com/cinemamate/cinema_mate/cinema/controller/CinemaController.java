package com.cinemamate.cinema_mate.cinema.controller;

import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdateCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdatePasswordDto;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@PreAuthorize("hasAuthority('CINEMA')")
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

    @PutMapping()
    public ResponseEntity<CinemaDto> updateCinemaInfo(@Valid @RequestBody UpdateCinemaDto updateCinemaDto, Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(cinemaService.updateCinema(cinemaName,updateCinemaDto));
    }

    @PutMapping("/profile")
    public  ResponseEntity<String> uploadProfileImage(MultipartFile imageFile,Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(cinemaService.uploadCinemaProfile(cinemaName,imageFile));
    }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto,Principal principal){
        String cinemaName = principal.getName();
        return ResponseEntity.ok(cinemaService.updatePassword(cinemaName,updatePasswordDto));
    }
}
