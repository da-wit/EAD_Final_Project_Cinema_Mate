package com.cinemamate.cinema_mate.user.services.userServiceImpl;

import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.core.service.ICinemaUserHelper;
import com.cinemamate.cinema_mate.core.service.IFileService;
import com.cinemamate.cinema_mate.user.dto.UpdatePasswordDto;
import com.cinemamate.cinema_mate.user.dto.UpdateUserDto;
import com.cinemamate.cinema_mate.user.dto.UserDto;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
import com.cinemamate.cinema_mate.user.mapper.UserMapper;
import com.cinemamate.cinema_mate.user.repository.UserRepository;
import com.cinemamate.cinema_mate.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ICinemaUserHelper cinemaUserHelper;
    private  final IFileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public UserDto getUserByUserName(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> UserExceptions.usernameNotFound(username));
        return userMapper.UsertoUserDto(user);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        userMapper.UsertoUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(String userName,UpdateUserDto updateUserDto) {
        User user = userRepository.findUserByUsername(userName).orElseThrow(() -> UserExceptions.usernameNotFound(userName));

        if (!user.getUsername().equals(updateUserDto.getUsername())){
            boolean userExists = cinemaUserHelper.isNameConflict(updateUserDto.getUsername());
            if(userExists){
                throw UserExceptions.usernameAlreadyTaken();
            }
        }
        if(!user.getEmail().equals(updateUserDto.getEmail())){
            User emailExists = userRepository.findUserByEmail(updateUserDto.getEmail()).orElse(null);
            if(emailExists != null){
                throw UserExceptions.emailAlreadyRegistered();
            }
        }
        user.setUsername(updateUserDto.getUsername());
        user.setEmail(updateUserDto.getEmail());

        userRepository.save(user);
        return  userMapper.UsertoUserDto(user);
    }

    @Override
    public UserDto getUserDetail(String userName) {
        User  user = userRepository.findByUsername(userName);
        if(user == null){
            throw UserExceptions.usernameNotFound();
        }
        return userMapper.UsertoUserDto(user);
    }

    @Override
    public String uploadProfileImage(String userName,MultipartFile imageFile) {
        User user = userRepository.findByUsername(userName);
        if(user == null){
            throw UserExceptions.usernameNotFound();
        }
        fileService.deleteUserImage(user.getProfileImage());
        String imagePath = fileService.saveUserImage(imageFile);
        user.setProfileImage(imagePath);
        userRepository.save(user);
        return  user.getId();
    }

    @Override
    public User getUserById(String id) {
        User user = userRepository.findUserById(id);
        if(user == null){
            throw UserExceptions.usernameNotFound();
        }
        return user;
    }

    @Override
    public String updatePassword(String userName, UpdatePasswordDto updatePasswordDto) {
        User user = userRepository.findByUsername(userName);
        if(user == null){
            throw UserExceptions.usernameNotFound();
        }
        boolean passwordMatch = passwordEncoder.matches(updatePasswordDto.getOldPassword(),user.getPassword());
        if(!passwordMatch){
            throw UserExceptions.incorrectOldPassword();
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        userRepository.save(user);
        return "password updated Successfully";
    }
}
