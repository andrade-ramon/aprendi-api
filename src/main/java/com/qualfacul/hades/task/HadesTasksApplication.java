package com.qualfacul.hades.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"com.qualfacul.hades"})
public class HadesTasksApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HadesTasksApplication.class, args);
	}
}