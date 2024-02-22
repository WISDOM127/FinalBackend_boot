package com.web;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinalProjectRightwayApplicationTests {

	@Test
	void contextLoads() {
		
		 LocalDateTime date = LocalDateTime.now();
			
		 System.out.println(date);
	}

}