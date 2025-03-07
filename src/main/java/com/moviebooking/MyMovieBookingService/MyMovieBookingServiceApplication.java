package com.moviebooking.MyMovieBookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration
public class MyMovieBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMovieBookingServiceApplication.class, args);
	}

}
