package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJpaAuditing
@SpringBootApplication
public class FinalProjectRightwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectRightwayApplication.class, args);
		System.out.println("스프링어플리케이션 실행");
	}

}
