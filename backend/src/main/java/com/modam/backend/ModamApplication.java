package com.modam.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.modam")
public class ModamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModamApplication.class, args);
	}

}
