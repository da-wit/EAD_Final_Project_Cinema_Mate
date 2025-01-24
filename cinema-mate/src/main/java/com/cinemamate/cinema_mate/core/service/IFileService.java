package com.cinemamate.cinema_mate.core.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String saveImage(MultipartFile image);
    void deleteImage(String imagePath);
    String saveUserImage(MultipartFile image);
    void deleteUserImage(String imageName);
    String saveCinemaImage(MultipartFile image);
    void deleteCinemaImage(String imageName);
}
