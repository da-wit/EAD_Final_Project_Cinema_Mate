package com.cinemamate.cinema_mate.core.security;

import com.cinemamate.cinema_mate.core.constant.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUsernameAndRole(String username, Role role) throws UsernameNotFoundException{

        if (role == null){
            // ToDo try to find the user in either user or cinema talbe

        }else if (role == Role.USER){
            //ToDo use the user repository here
            return null;
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
