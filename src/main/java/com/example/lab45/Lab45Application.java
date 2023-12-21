package com.example.lab45;

import com.example.lab45.Task.utils.DateParser;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
		info = @Info(

				title = "Task Manager API",
				description = "This is project for Spring labs"

		),
		servers = {

				@Server(url = "http://localhost:8080", description = "test server"),
				@Server(url = "http://example.com", description = "production server")
		}

)
@SpringBootApplication
public class Lab45Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab45Application.class, args);
	}

	@Bean
	DateParser dateParser() { return new DateParser(); }
}
