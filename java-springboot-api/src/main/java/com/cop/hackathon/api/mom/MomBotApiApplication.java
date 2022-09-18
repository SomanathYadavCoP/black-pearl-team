package com.cop.hackathon.api.mom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MomBotApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(MomBotApiApplication.class, args);
	}

}
