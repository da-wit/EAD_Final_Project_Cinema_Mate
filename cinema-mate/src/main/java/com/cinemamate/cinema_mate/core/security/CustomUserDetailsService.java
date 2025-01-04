package com.cinemamate.cinema_mate.core.security;

import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.services.userServiceImpl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsernameAndRole(username, null);
    }

    public UserDetails loadUserByUsernameAndRole(String username, Role role) throws UsernameNotFoundException{

        if (role == null){
            // ToDo try to find the user in either user or cinema talbe
            // user implmented
            System.out.println("with role null");
            System.out.println(username);

            User user = userService.getUser(username);
            if(user != null){
                return user;
            }
        }else if (role == Role.USER){
            User user = userService.getUser(username);
            System.out.println(username);
            System.out.println(role);
            if(user != null){
                return user;
            }
        }else if (role == Role.CINEMA){
            // ToDo use the Cinema repository here
            return null;
        } else if (role == Role.ADMIN) {
            // ToDo use the User repository here
            return null;
        };

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
