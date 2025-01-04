package com.cinemamate.cinema_mate.auth.services.authenticationServiceImpl;

import com.cinemamate.cinema_mate.auth.dto.AuthResponse;
import com.cinemamate.cinema_mate.auth.services.IAuthenticationService;
import com.cinemamate.cinema_mate.auth.util.JwtUtil;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.user.dto.CreateUserDto;
import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.mapper.UserMapper;
import com.cinemamate.cinema_mate.user.repository.UserRepository;
import com.cinemamate.cinema_mate.user.services.IUserService;
import com.cinemamate.cinema_mate.user.services.userServiceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    // ToDo git the Cinema repo to check the user name uniqueness

    @Override
    public AuthResponse userRegister(CreateUserDto createUserDto) {
        boolean userExists = userService.userExists(createUserDto.getUsername());
        System.out.println("sdkfkdshfdf");
        System.out.println(userExists);
        if (userExists){
           throw  new RuntimeException("User name already taken");
        }
        // ToDo check if the user name exists in the cinema
        User user = User.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .isActive(true)
                .role(Role.USER)
                .build();
        userService.saveUser(user);
        String token = jwtUtil.generateToken(user);
        return new AuthResponse().builder()
                .token(token).build();
    }
}
