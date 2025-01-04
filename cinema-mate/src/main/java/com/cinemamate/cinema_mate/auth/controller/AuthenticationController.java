package com.cinemamate.cinema_mate.auth.controller;

import com.cinemamate.cinema_mate.auth.dto.AuthRequest;
import com.cinemamate.cinema_mate.auth.dto.AuthResponse;
import com.cinemamate.cinema_mate.auth.services.authenticationServiceImpl.AuthenticationService;
import com.cinemamate.cinema_mate.user.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    // Register user
    @PostMapping("/user")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody CreateUserDto createUserDto){
        return new ResponseEntity<>(authenticationService.userRegister(createUserDto), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
