package com.cinemamate.cinema_mate.auth.services.authenticationServiceImpl;

import com.cinemamate.cinema_mate.auth.exceptions.AuthExceptions;
import com.cinemamate.cinema_mate.admin.services.IAdminService;
import com.cinemamate.cinema_mate.auth.dto.AuthRequest;
import com.cinemamate.cinema_mate.auth.dto.AuthResponse;
import com.cinemamate.cinema_mate.auth.services.IAuthenticationService;
import com.cinemamate.cinema_mate.auth.util.JwtUtil;
import com.cinemamate.cinema_mate.cinema.dto.CreateCinemaDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.core.security.CustomUserDetailsService;
import com.cinemamate.cinema_mate.user.dto.CreateUserDto;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.services.IUserService;
import com.cinemamate.cinema_mate.core.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;
    private final ICinemaService cinemaService;
    private final IAdminService adminService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponse userRegister(CreateUserDto createUserDto) {
        String username = createUserDto.getUsername();
        boolean userExists = userService.userExists(username);
        System.out.println("sdkfkdshfdf");
        System.out.println(userExists);
        if (userExists) {
            throw AuthExceptions.userNameAlreadyTaken();
        }
        // ToDo check if the user name exists in the cinema
        boolean cinemaExists = cinemaService.cinemaExists(username);
        if (cinemaExists) {
            throw AuthExceptions.userNameAlreadyTaken();
        }

        boolean adminExists = adminService.adminExists(username);
        if(adminExists){
            throw AuthExceptions.userNameAlreadyTaken();
        }
        User user = User.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .isActive(true)
                .role(Role.USER)
                .build();
        userService.saveUser(user);

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token).build();
    }

    @Override
    public AuthResponse cinemaRegister(CreateCinemaDto createCinemaDto) {
        String username = createCinemaDto.getCinemaName();
        boolean userExists = userService.userExists(username);
        System.out.println("sdkfkdshfdf");
        System.out.println(userExists);
        if (userExists) {
            throw AuthExceptions.userNameAlreadyTaken();
        }
        // ToDo check if the user name exists in the cinema
        boolean cinemaExists = cinemaService.cinemaExists(username);
        if (cinemaExists) {
            throw AuthExceptions.userNameAlreadyTaken();
        }

        boolean adminExists = adminService.adminExists(username);
        if(adminExists){
            throw AuthExceptions.userNameAlreadyTaken();
        }

        Cinema cinema = Cinema.builder()
                .cinemaName(createCinemaDto.getCinemaName())
                .email(createCinemaDto.getEmail())
                .password(passwordEncoder.encode(createCinemaDto.getPassword()))
                .description(createCinemaDto.getDescription())
                .isActive(true)
                .role(Role.CINEMA)
                .build();

        cinemaService.saveCinema(cinema);

        String token = jwtUtil.generateToken(cinema);

        return AuthResponse.builder()
                .token(token).build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        System.out.println("in the service");
        System.out.println(request);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            System.out.println("inside the try block");

            String username = authentication.getName();
            String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No role found for the user"));

            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsernameAndRole(username, Role.valueOf(role));
            System.out.println("before generating the token");
            String token = jwtUtil.generateToken(userDetails);

            return AuthResponse.builder()
                    .token(token).build();

        } catch (BadCredentialsException e) {
            throw AuthExceptions.invalidCredentials();
        }
    }
}
