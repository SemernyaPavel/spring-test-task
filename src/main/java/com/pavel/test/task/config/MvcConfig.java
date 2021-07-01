package com.pavel.test.task.config;

import org.springframework.boot.autoconfigure.domain.EntityScan; 
import org.springframework.context.annotation.ComponentScan; 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.pavel.test.task")
@EntityScan("com.pavel.test.task")
public class MvcConfig implements WebMvcConfigurer{
	
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
}