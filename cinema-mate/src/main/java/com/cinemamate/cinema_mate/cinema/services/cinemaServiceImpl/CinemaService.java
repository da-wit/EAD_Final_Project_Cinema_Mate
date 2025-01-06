package com.cinemamate.cinema_mate.cinema.services.cinemaServiceImpl;

import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.cinema.mapper.CinemaMapper;
import com.cinemamate.cinema_mate.cinema.repository.CinemaRepository;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CinemaService implements ICinemaService {

    private final CinemaRepository cinemaRepository;

    @Override
    public CinemaDto getCinemaByCinemaName(String cinemaName) {
        Cinema cinema = cinemaRepository.findCinemaByCinemaname(cinemaName).orElseThrow(() -> CinemaExceptions.cinemaNameNotFound(cinemaName));
        return CinemaMapper.cinemaToCinemaDto(cinema);
    }

    @Override
    public boolean cinemaExists(String cinemaName) {
        Cinema cinema = cinemaRepository.findCinemaByCinemaname(cinemaName).orElse(null);
        if (cinema != null) {
            return true;
        }
        return false;
    }

    @Override
    public Cinema getCinema(String cinemaName) {
        return cinemaRepository.findCinemaByCinemaname(cinemaName).orElse(null);
    }

    @Override
    public CinemaDto saveCinema(Cinema cinema) {
        cinemaRepository.save(cinema);

        return CinemaMapper.cinemaToCinemaDto(cinema);
    }

    @Override
    public Cinema getCinemaById(String id) {
        return cinemaRepository.findCinemaById(id).orElse(null);
    }


}
