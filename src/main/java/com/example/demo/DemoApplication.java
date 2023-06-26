package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SpringBootApplication
public class DemoApplication {

/*	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;*/


	public static void main(String[] args) {
		git config --global user.email "bykrealno@gmail.com"
		git config --global user.name "Yauheni"
		SpringApplication.run(DemoApplication.class, args);
	}


}
