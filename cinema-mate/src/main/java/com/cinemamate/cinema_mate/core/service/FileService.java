package com.cinemamate.cinema_mate.core.service;

import com.cinemamate.cinema_mate.core.config.FileStorageConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileStorageConfig fileConfig;

    public String saveImage(MultipartFile imageFile){
        try{

            String uploadDir = fileConfig.getUploadDir();
            Path directoryPath = Paths.get(uploadDir);

            if(Files.notExists(directoryPath)){
                Files.createDirectories(directoryPath);
            }

            String originalFileName = imageFile.getOriginalFilename();
            String uniqueName = UUID.randomUUID().toString()+"_"+originalFileName;

            Path filePath = directoryPath.resolve(uniqueName);

            imageFile.transferTo(filePath.toFile());

            return fileConfig.getMoviePath()+uniqueName;

        }catch (IOException e){
            throw new RuntimeException("Failed to upload image");
        }
    }
}
