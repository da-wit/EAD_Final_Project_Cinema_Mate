package com.cinemamate.cinema_mate.core.config;

import com.cinemamate.cinema_mate.auth.config.JwtAuthenticationFilter;
import com.cinemamate.cinema_mate.core.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService customUserDetailsService;

    private final AuthenticationEntryPoint authenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.
                csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(
                        authz ->authz.requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/pages/**","/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/movieImage/**").permitAll()
                                .requestMatchers("/userProfile/**").permitAll()
                                .requestMatchers("/cinemaProfile/**").permitAll()
//                                .requestMatchers("/api/v1/cinema/**").authenticated()
//                                .requestMatchers("/api/v1/user/**").authenticated()
//                                .requestMatchers("/api/v1/movie/**").authenticated()
//                                .requestMatchers("/api/v1/watchlist/**").authenticated()
//                                .requestMatchers("/api/v1/booking/**").authenticated()
//                                .requestMatchers("/api/v1/movie/**").permitAll()
//                                .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
             )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
