package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//start-class
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.gcit.lms")
public class LmsBootAngularJsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsBootAngularJsApplication.class, args);
	}
}
