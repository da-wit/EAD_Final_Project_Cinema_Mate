package com.cinemamate.cinema_mate.cinema.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCinemaDto {
    @NotEmpty
    @Size(min = 3,message = "CinemaName can not be less than 3 characters.")
    private String cinemaName;
    @Email
    private String email;
    @NotEmpty
    @Size(min = 6,message = "Password can not be less than 6 characters")
    private String password;
    @NotEmpty
    @Size(min = 6,message = "Password can not be less than 6 characters")
    private String confirmPassword;
    @NotEmpty
    private String description;
    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatching() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }
}
