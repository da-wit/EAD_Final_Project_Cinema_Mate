package com.cinemamate.cinema_mate.cinema.services.cinemaServiceImpl;

import com.cinemamate.cinema_mate.cinema.dto.BasicCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.CinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdateCinemaDto;
import com.cinemamate.cinema_mate.cinema.dto.UpdatePasswordDto;
import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.cinema.exceptions.CinemaExceptions;
import com.cinemamate.cinema_mate.cinema.mapper.CinemaMapper;
import com.cinemamate.cinema_mate.cinema.repository.CinemaRepository;
import com.cinemamate.cinema_mate.cinema.services.ICinemaService;
import com.cinemamate.cinema_mate.core.service.ICinemaUserHelper;
import com.cinemamate.cinema_mate.core.service.fileServiceImpl.FileService;
import com.cinemamate.cinema_mate.movie.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaService implements ICinemaService {

    private final CinemaRepository cinemaRepository;
    private final ICinemaUserHelper cinemaUserHelper;
    private final FileService fileService;
    private  final  PasswordEncoder passwordEncoder;
    private final CinemaMapper cinemaMapper;

    @Override
    public CinemaDto getCinemaByCinemaName(String cinemaName) {
        Cinema cinema = cinemaRepository.findCinemaByCinemaName(cinemaName).orElseThrow(() -> CinemaExceptions.cinemaNameNotFound(cinemaName));
        return cinemaMapper.cinemaToCinemaDto(cinema);
    }

    @Override
    public boolean cinemaExists(String cinemaName) {
        return cinemaRepository.existsByCinemaName(cinemaName);
    }

    @Override
    public Cinema getCinema(String cinemaName) {
        return cinemaRepository.findCinemaByCinemaName(cinemaName).orElse(null);
    }

    @Override
    public CinemaDto saveCinema(Cinema cinema) {
        cinemaRepository.save(cinema);

        return cinemaMapper.cinemaToCinemaDto(cinema);
    }

    @Override
    public Cinema getCinemaById(String id) {
        return cinemaRepository.findCinemaById(id).orElseThrow(()-> CinemaExceptions.notFound(id));
    }

    @Override
    public
    CinemaDto updateCinema(String cinemaName, UpdateCinemaDto updateCinemaDto) {
        Cinema cinema = cinemaRepository.findCinemaByCinemaName(cinemaName).orElseThrow(()-> CinemaExceptions.cinemaNameNotFound(cinemaName));
        if(!cinema.getCinemaName().equals(updateCinemaDto.getCinemaName())){
            boolean cinemaNameExists = cinemaUserHelper.isNameConflict(updateCinemaDto.getCinemaName());
            if (cinemaNameExists){
                throw CinemaExceptions.cinemaNameAlreadyTaken();
            }
        }
        if(!cinema.getEmail().equals(updateCinemaDto.getEmail())){
            boolean emailExists = cinemaRepository.existsByEmail(updateCinemaDto.getEmail());
            if(emailExists){
                throw CinemaExceptions.emailAlreadyRegistered();
            }
        }
        cinema.setCinemaName(updateCinemaDto.getCinemaName());
        cinema.setEmail(updateCinemaDto.getEmail());
        cinema.setDescription(updateCinemaDto.getDescription());
        cinemaRepository.save(cinema);
//        String token = jwtUtil.generateToken(cinema);

        return cinemaMapper.cinemaToCinemaDto(cinema);
    }

    @Override
    public CinemaDto getCinemaDetail(String cinemaName) {
        Cinema cinema = cinemaRepository.findCinemaByCinemaName(cinemaName).orElseThrow(() -> CinemaExceptions.cinemaNameNotFound(cinemaName));
        return cinemaMapper.cinemaToCinemaDto(cinema);
    }

    @Override
    public String uploadCinemaProfile(String cinemaName, MultipartFile imageFile) {

        Cinema cinema = cinemaRepository.findCinemaByCinemaName(cinemaName).orElseThrow(() -> CinemaExceptions.cinemaNameNotFound(cinemaName));
        System.out.println(cinema.getCinemaName());
        fileService.deleteCinemaImage(cinema.getProfileImage());
        String imagePath = fileService.saveCinemaImage(imageFile);
        cinema.setProfileImage(imagePath);
        cinemaRepository.save(cinema);
        return cinema.getId();
    }

    @Override
    public String updatePassword(String cinemaName, UpdatePasswordDto updatePasswordDto) {
        Cinema cinema = cinemaRepository.findCinemaByCinemaName(cinemaName).orElseThrow(()-> CinemaExceptions.cinemaNameNotFound(cinemaName));
        boolean passwordMatches = passwordEncoder.matches(updatePasswordDto.getOldPassword(),cinema.getPassword());
        if(!passwordMatches){
            throw CinemaExceptions.incorrectOldPassword();
        }
        cinema.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
        cinemaRepository.save(cinema);
        return "password updated successfully";
    }

    @Override
    public List<CinemaDto> getAllCinema(String search) {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemas.stream().filter(cinema -> search == null || search.isEmpty() ||
                cinema.getCinemaName().toLowerCase().contains(search.toLowerCase())).sorted(Comparator.comparing(Cinema::getCreatedAt)).map(cinemaMapper::cinemaToCinemaDto).collect(Collectors.toList());
    }

    @Override
    public BasicCinemaDto getCinemaDetailById(String id) {
        Cinema cinema =cinemaRepository.findCinemaById(id).orElseThrow(()->CinemaExceptions.notFound(id));
        return cinemaMapper.cinemaToBasicCinemaDto(cinema);
    }


}
