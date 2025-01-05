package com.cinemamate.cinema_mate.user.dto;

import com.cinemamate.cinema_mate.core.constant.Role;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateUserDto {
    @NotEmpty
    @Size(min = 3,message = "UserName can not be less than 3d characters")
    private String username;
    @NotEmpty
    @Email
    private String email;
    @Size(min = 6,message = "Password can not be less than 6 characters")
    private String password;
    @NotEmpty
    @Size(min = 6,message = "ConfirmPassword can not be less than 6 characters")
    private String confirmPassword;
    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatching() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }
}
