package com.cinemamate.cinema_mate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CinemaMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaMateApplication.class, args);
	}

}
