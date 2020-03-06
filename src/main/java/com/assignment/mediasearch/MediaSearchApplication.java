package com.assignment.mediasearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MediaSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaSearchApplication.class, args);
	}

}
