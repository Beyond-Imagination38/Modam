package com.modam.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "com.modam")
public class ModamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModamApplication.class, args);
	}

}
