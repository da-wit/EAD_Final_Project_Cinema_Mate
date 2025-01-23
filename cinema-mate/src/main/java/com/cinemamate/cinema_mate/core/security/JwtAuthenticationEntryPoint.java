package com.cinemamate.cinema_mate.core.security;

import com.cinemamate.cinema_mate.auth.exceptions.AuthExceptions;
import com.cinemamate.cinema_mate.core.entity.ErrorDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String errorMessage = (String) request.getAttribute("exceptionMessage");
        if (errorMessage == null) {
            errorMessage = "Full authentication is required to access this resource";
        }

        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(errorMessage)
                .details("uri=" + request.getRequestURI())
                .timestamp(new Date())
                .build();

        // Convert ErrorDetail to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorDetail);

        response.getWriter().write(jsonResponse);
    }
}
