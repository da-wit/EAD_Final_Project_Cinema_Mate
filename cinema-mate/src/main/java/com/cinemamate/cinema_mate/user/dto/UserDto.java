package com.cinemamate.cinema_mate.user.dto;

import com.cinemamate.cinema_mate.core.constant.Role;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String profileImage;
    private boolean isActive;
    private Role role;
}
