package com.cinemamate.cinema_mate.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FileStorageConfig {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${file.movie-path}")
    private String moviePath;

}
