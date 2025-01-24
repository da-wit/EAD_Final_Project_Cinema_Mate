package com.cinemamate.cinema_mate.user.services;

import com.cinemamate.cinema_mate.user.dto.UpdatePasswordDto;
import com.cinemamate.cinema_mate.user.dto.UpdateUserDto;
import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    UserDto getUserByUserName(String username);
    boolean userExists(String username);
    User getUser(String username);
    void saveUser(User user);
    UserDto updateUser(String userName,UpdateUserDto updateUserDto);
    UserDto getUserDetail(String userName);
    String uploadProfileImage(String userName,MultipartFile imageFile);
    User getUserById(String id);
    String updatePassword(String userName, UpdatePasswordDto updatePasswordDto);
}
