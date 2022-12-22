package com.example.moduhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ModuhouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuhouseApplication.class, args);
	}
}