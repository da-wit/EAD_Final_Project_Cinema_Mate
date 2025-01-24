package com.cinemamate.cinema_mate.user.controller;

import com.cinemamate.cinema_mate.user.dto.UpdatePasswordDto;
import com.cinemamate.cinema_mate.user.dto.UpdateUserDto;
import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping()
    public ResponseEntity<UserDto> getUserDetail(Principal principal){
        String userName = principal.getName();
        return ResponseEntity.ok(userService.getUserDetail(userName));
    }

    @PutMapping("/profile")
    public ResponseEntity<String> uploadProfileImage(MultipartFile imageFile,Principal principal){
        String userName = principal.getName();
        return ResponseEntity.ok(userService.uploadProfileImage(userName,imageFile));
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateUserNameAndPassword(
            @Valid @RequestBody UpdateUserDto updateUserDto,
            Principal principal
            ){
        String userName = principal.getName();
        return ResponseEntity.ok(userService.updateUser(userName,updateUserDto));
    }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto,Principal principal){
        String userName = principal.getName();
        return ResponseEntity.ok(userService.updatePassword(userName,updatePasswordDto));
    }



}
