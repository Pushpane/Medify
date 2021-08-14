package com.psl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Medify API", version = "1.0", description = "Medical Store Information"))
@EnableAsync
public class MedifyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedifyApiApplication.class, args);
	}

}
