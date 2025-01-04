package com.cinemamate.cinema_mate.user.dto;

import com.cinemamate.cinema_mate.core.constant.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
}
