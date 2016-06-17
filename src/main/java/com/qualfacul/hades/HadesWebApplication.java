package com.qualfacul.hades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.annotation.TaskService;
import com.qualfacul.hades.annotation.WebComponent;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter({TaskComponent.class,
										 TaskService.class}))
@WebComponent
public class HadesWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(HadesWebApplication.class, args);
	}
}
