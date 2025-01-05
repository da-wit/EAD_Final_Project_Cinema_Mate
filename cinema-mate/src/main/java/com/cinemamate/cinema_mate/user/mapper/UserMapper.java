package com.cinemamate.cinema_mate.user.mapper;

import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;

public class UserMapper {

    public static UserDto UsertoUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .role(user.getRole())
                .build();
    }

//    public  static User createUserDtoToUser (CreateUserDto createUserDto){
//
//        return User.builder()
//                .username(createUserDto.getUsername())
//                .email(createUserDto.getEmail())
//                .password(PasswordEncoder.)
//                .isActive(createUserDto.isActive())
//                .role(createUserDto.getRole())
//                .build();
//    }
}
