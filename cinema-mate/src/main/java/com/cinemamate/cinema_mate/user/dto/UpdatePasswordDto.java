package com.cinemamate.cinema_mate.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePasswordDto {
    @NotEmpty
    @Size(min = 6,message = "Password can not be less than 6 characters")
    private String oldPassword;
    @NotEmpty
    @Size(min = 6,message = "password can not be less than 6 characters")
    private String newPassword;
}
