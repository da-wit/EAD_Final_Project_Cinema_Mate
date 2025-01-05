package com.cinemamate.cinema_mate.user.services;

import com.cinemamate.cinema_mate.user.dto.CreateUserDto;
import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;

public interface IUserService {
    UserDto getUserByUserName(String username);
    boolean userExists(String username);
    User getUser(String username);
    UserDto saveUser(User user);
}
