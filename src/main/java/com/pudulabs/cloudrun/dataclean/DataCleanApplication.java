package com.pudulabs.cloudrun.dataclean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = { "com.pudulabs.cloudrun.dataclean" })
public class DataCleanApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataCleanApplication.class, args);
	}

}
