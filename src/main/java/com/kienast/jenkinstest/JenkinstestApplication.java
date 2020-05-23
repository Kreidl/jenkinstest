package com.kienast.jenkinstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JenkinstestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenkinstestApplication.class, args);
		
		String api_key = "374d66dad8b2b10a54437d04e5fa83819a10b752";
		
		System.out.println(api_key);
	}

}
