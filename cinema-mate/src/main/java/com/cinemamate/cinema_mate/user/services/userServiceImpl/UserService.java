package com.cinemamate.cinema_mate.user.services.userServiceImpl;

import com.cinemamate.cinema_mate.user.dto.CreateUserDto;
import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import com.cinemamate.cinema_mate.user.mapper.UserMapper;
import com.cinemamate.cinema_mate.user.repository.UserRepository;
import com.cinemamate.cinema_mate.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;


    @Override
    public UserDto getUserByUserName(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> UserExceptions.usernameNotFound(username));
        return UserMapper.UsertoUserDto(user);
    }

    @Override
    public boolean userExists(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println("user service");
        System.out.println(user);
        System.out.println(user == null);
        if (user != null){
            return true;
        }
        return false;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Override
    public UserDto saveUser(User user) {
        return UserMapper.UsertoUserDto(userRepository.save(user));
    }
}
