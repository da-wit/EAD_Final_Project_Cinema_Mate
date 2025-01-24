package com.cinemamate.cinema_mate.core.service.cinemaUserHelperImpl;

import com.cinemamate.cinema_mate.cinema.repository.CinemaRepository;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.core.service.ICinemaUserHelper;
import com.cinemamate.cinema_mate.user.repository.UserRepository;
import com.cinemamate.cinema_mate.user.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaUserHelper implements ICinemaUserHelper {
    private final UserRepository userRepository;
    private final CinemaRepository cinemaRepository;
    @Override
    public boolean isNameConflict(String name) {
        return cinemaRepository.existsByCinemaName(name) || userRepository.existsByUsername(name);
    }
}
