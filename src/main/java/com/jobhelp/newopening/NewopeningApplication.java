package com.jobhelp.newopening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewopeningApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewopeningApplication.class, args);
	}

}
