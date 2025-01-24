package com.cinemamate.cinema_mate.core.security;

import com.cinemamate.cinema_mate.core.constant.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ICustomUserDetailsService extends UserDetailsService {
    CustomUserDetails loadUser(String username, Role role);
    CustomUserDetails loadUserByUsernameRoleAndId(String username, Role role, String id);

}
