package com.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.domain.Member;

@RestController
public class MainController {

	@GetMapping("/test")
	public void test() {
		System.out.println("test");
		
	}
	
	//axios 전송 온 데이터를 Member 엔티티 타입으로 수령
	@PostMapping("/SignUser")
	public void SignUser(@RequestBody Member member) {
		System.out.println("SignUser" );
		System.out.println(member);
		System.out.println();
		
		
		
		
	}
}
