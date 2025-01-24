package com.cinemamate.cinema_mate.auth.config;

import com.cinemamate.cinema_mate.auth.exceptions.AuthExceptions;
import com.cinemamate.cinema_mate.auth.util.JwtUtil;
import com.cinemamate.cinema_mate.core.constant.Role;
import com.cinemamate.cinema_mate.core.security.CustomUserDetailsService;
import com.cinemamate.cinema_mate.core.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        final Role role;
        final String id;

        try{

        if (authHeader == null || !authHeader.startsWith("Bearer ") || !jwtUtil.validateToken(authHeader.substring(7)) ){
            filterChain.doFilter(request,response);
            return;
        }


        jwt = authHeader.substring(7);
        username =jwtUtil.extractUsername(jwt);
        role = jwtUtil.extractRole(jwt);
        id = jwtUtil.extractId(jwt);


        if (username == null || SecurityContextHolder.getContext().getAuthentication() == null){


//            UsersInterface userDetails = customUserDetailsService.loadUserByUsernameAndRole(username,role);
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsernameRoleAndId(username,role,id);


            if (jwtUtil.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // here catch any auth exception is thrown
        }catch (AuthExceptions ex) {
            request.setAttribute("exceptionMessage", ex.getMessage());
        }
        filterChain.doFilter(request,response);

    }
}
