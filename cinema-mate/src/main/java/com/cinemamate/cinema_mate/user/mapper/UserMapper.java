package com.cinemamate.cinema_mate.user.mapper;

import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto UsertoUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
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
