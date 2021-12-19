package com.gaurav.restaurantsservice;

import com.gaurav.restaurantsservice.checkers.RestaurantChecker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantsServiceApplication.class, args);
	}

	@Bean
	public RestaurantChecker getRestaurantChecker(){
		return new RestaurantChecker();
	}

}
