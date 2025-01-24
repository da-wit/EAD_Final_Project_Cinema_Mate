package com.cinemamate.cinema_mate.core.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/movieImage/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/assets/movieImage/");
        registry.addResourceHandler("/userProfile/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/assets/userProfile");
        registry.addResourceHandler("/cinemaProfile/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/assets/cinemaProfile");
    }
}

