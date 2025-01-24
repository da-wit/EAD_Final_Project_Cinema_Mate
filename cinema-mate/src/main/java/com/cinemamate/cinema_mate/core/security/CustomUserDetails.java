package com.cinemamate.cinema_mate.core.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    String getId();
}
