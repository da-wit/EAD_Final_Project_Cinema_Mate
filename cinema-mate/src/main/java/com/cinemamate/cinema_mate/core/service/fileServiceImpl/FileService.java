package com.cinemamate.cinema_mate.core.service.fileServiceImpl;

import com.cinemamate.cinema_mate.core.config.FileStorageConfig;
import com.cinemamate.cinema_mate.core.exceptions.FileExceptions;
import com.cinemamate.cinema_mate.core.service.IFileService;
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
public class FileService implements IFileService {
    private final FileStorageConfig fileConfig;

    public String saveImage(MultipartFile imageFile){
        try{

            if (imageFile == null || imageFile.isEmpty()) {
                throw FileExceptions.noImageSelected();
            }

            String projectDir = System.getProperty("user.dir");

            String uploadDir = Paths.get(projectDir, "src", "assets", "movieImage").toString();
            Path directoryPath = Paths.get(uploadDir);

            if(Files.notExists(directoryPath)){
                Files.createDirectories(directoryPath);
            }

            String originalFileName = imageFile.getOriginalFilename();
            String uniqueName = UUID.randomUUID().toString()+"_"+originalFileName;

            Path filePath = directoryPath.resolve(uniqueName);


            imageFile.transferTo(filePath.toFile());

            return uniqueName;

        }catch (IOException e){
            e.printStackTrace();
            throw FileExceptions.failedToUploadImage();
        }
    }


    public void deleteImage(String imagePath) {
        try {

            String projectDir = System.getProperty("user.dir");


            String fileName = Paths.get(imagePath).getFileName().toString();


            String uploadDir = Paths.get(projectDir, "src", "assets", "movieImage").toString();
            Path filePath = Paths.get(uploadDir, fileName);


            if (Files.exists(filePath)) {
                Files.delete(filePath);
//                System.out.println("Image deleted successfully: " + fileName);
            }
//            else {
//                System.out.println("Image not found: " + fileName);
//                throw new RuntimeException("Image not found: " + fileName);
//            }
        } catch (IOException e) {
            e.printStackTrace();
            throw FileExceptions.ioException(e.getMessage());
        }
    }

    @Override
    public String saveUserImage(MultipartFile image) {
        try{

            if (image == null || image.isEmpty()) {
                throw FileExceptions.noImageSelected();
            }

            String projectDir = System.getProperty("user.dir");

            String uploadDir = Paths.get(projectDir, "src", "assets", "userProfile").toString();
            Path directoryPath = Paths.get(uploadDir);

            if(Files.notExists(directoryPath)){
                Files.createDirectories(directoryPath);
            }

            String originalFileName = image.getOriginalFilename();
            String uniqueName = UUID.randomUUID().toString()+"_"+originalFileName;

            Path filePath = directoryPath.resolve(uniqueName);


            image.transferTo(filePath.toFile());

            return uniqueName;

        }catch (IOException e){
            e.printStackTrace();
            throw FileExceptions.failedToUploadImage();
        }
    }

    @Override
    public void deleteUserImage(String imageName) {
        try {

            String projectDir = System.getProperty("user.dir");
            if(imageName == null){
                return;
            }


            String fileName = Paths.get(imageName).getFileName().toString();


            String uploadDir = Paths.get(projectDir, "src", "assets", "userProfile").toString();
            Path filePath = Paths.get(uploadDir, fileName);


            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Image deleted successfully: " + fileName);
            }
//            else {
//                System.out.println("Image not found: " + fileName);
//                throw FileExceptions.failedToUploadImage();
//            }
        } catch (IOException e) {
            e.printStackTrace();
            throw FileExceptions.ioException(e.getMessage());
        }
    }

    @Override
    public String saveCinemaImage(MultipartFile image) {
        try{

            if (image == null || image.isEmpty()) {
                throw FileExceptions.noImageSelected();
            }

            String projectDir = System.getProperty("user.dir");

            String uploadDir = Paths.get(projectDir, "src", "assets", "cinemaProfile").toString();
            Path directoryPath = Paths.get(uploadDir);

            if(Files.notExists(directoryPath)){
                Files.createDirectories(directoryPath);
            }

            String originalFileName = image.getOriginalFilename();
            String uniqueName = UUID.randomUUID().toString()+"_"+originalFileName;

            Path filePath = directoryPath.resolve(uniqueName);


            image.transferTo(filePath.toFile());

            return uniqueName;

        }catch (IOException e){
            e.printStackTrace();
            throw FileExceptions.failedToUploadImage();
        }
    }

    @Override
    public void deleteCinemaImage(String imageName) {
        try {

            String projectDir = System.getProperty("user.dir");

            if(imageName == null){
                return;
            }

            String fileName = Paths.get(imageName).getFileName().toString();


            String uploadDir = Paths.get(projectDir, "src", "assets", "cinemaProfile").toString();
            Path filePath = Paths.get(uploadDir, fileName);


            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Image deleted successfully: " + fileName);
            }
//            else {
//                System.out.println("Image not found: " + fileName);
//                throw new RuntimeException("Image not found: " + fileName);
//            }
        } catch (IOException e) {
            e.printStackTrace();
            throw FileExceptions.ioException(e.getMessage());
        }
    }
}
