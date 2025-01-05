package com.cinemamate.cinema_mate.core.security;

import com.cinemamate.cinema_mate.admin.entity.Admin;
import com.cinemamate.cinema_mate.admin.services.adminServiceImpl.AdminService;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.cinema.services.cinemaServiceImpl.CinemaService;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.user.entity.User;
import com.cinemamate.cinema_mate.user.exceptions.UserExceptions;
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
    private final CinemaService cinemaService;
    private final AdminService adminService;

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

            Cinema cinema =cinemaService.getCinema(username);
            if(cinema != null){
                return  cinema;
            }
            Admin admin = adminService.getAdmin(username);
            if(admin !=null){
                return admin;
            }

        }else if (role == Role.USER){
            User user = userService.getUser(username);
            System.out.println(username);
            System.out.println(role);
            if(user == null){
                UserExceptions.usernameNotFound(username);
            }
            return user;
        }else if (role == Role.CINEMA){
            // ToDo use the Cinema repository here
            Cinema cinema = cinemaService.getCinema(username);
            if(cinema == null) {
                CinemaExceptions.cinemaNameNotFound(username);
            }
            return cinema;
        } else if (role == Role.ADMIN) {
            // ToDo use the User repository here
            Admin admin = adminService.getAdmin(username);
            if(admin !=null){
                return admin;
            }
        };

        throw new UsernameNotFoundException("User/Cinema not found with username: " + username);
    }
}
