package com.example.demo21;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.example.demo21.entity","com.example.demo21.controller"})
public class Demo21Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo21Application.class, args);
	}

}
