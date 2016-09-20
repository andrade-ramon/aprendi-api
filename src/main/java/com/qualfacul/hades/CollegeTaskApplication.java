
package com.qualfacul.hades;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.annotation.WebConfiguration;
import com.qualfacul.hades.annotation.WebService;
import com.qualfacul.hades.task.MecCollegeTask;

@SpringBootApplication
@EnableScheduling
@TaskComponent
@EnableAutoConfiguration(exclude = WebMvcAutoConfiguration.class)
@ComponentScan(excludeFilters = @Filter({ RestController.class, WebComponent.class, WebService.class, WebConfiguration.class }) )
public class CollegeTaskApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(CollegeTaskApplication.class).web(false).run(args);
		MecCollegeTask bean = context.getBean(MecCollegeTask.class);
		bean.getAllColleges();
	}
}