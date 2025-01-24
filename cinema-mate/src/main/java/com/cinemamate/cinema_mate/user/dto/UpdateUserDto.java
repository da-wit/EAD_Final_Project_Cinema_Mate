package com.cinemamate.cinema_mate.user.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    @NotEmpty
    @Size(min = 3,message = "UserName can not be less than 3d characters")
    private String username;
    @NotEmpty
    @Email
    private String email;
}

