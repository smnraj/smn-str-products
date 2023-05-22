package com.shi.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ShiProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiProductsApplication.class, args);
	}

}
