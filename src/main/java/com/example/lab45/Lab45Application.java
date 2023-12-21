package com.example.lab45;

import com.example.lab45.Task.utils.DateParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab45Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab45Application.class, args);
	}

	@Bean
	DateParser dateParser() { return new DateParser(); }
}
