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


            System.out.println("inside file service");

            if (imageFile == null || imageFile.isEmpty()) {
                throw new RuntimeException("No file uploaded");
            }

            String projectDir = System.getProperty("user.dir");
            System.out.println(projectDir);

            String uploadDir = Paths.get(projectDir, "src", "assets", "movieImage").toString();
            Path directoryPath = Paths.get(uploadDir);

            if(Files.notExists(directoryPath)){
                Files.createDirectories(directoryPath);
            }

            String originalFileName = imageFile.getOriginalFilename();
            String uniqueName = UUID.randomUUID().toString()+"_"+originalFileName;

            Path filePath = directoryPath.resolve(uniqueName);
            System.out.println("first breaking point");

            imageFile.transferTo(filePath.toFile());

            System.out.println("Second breaking point");
            return fileConfig.getMoviePath()+uniqueName;

        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image");
        }
    }
}
