package com.example.Premind_BE;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class PremindBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(PremindBeApplication.class, args);
	}

}
