package com.rp.studentws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.rp.studentws.soap.bean.Course;

@SpringBootApplication
public class StudentWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentWsApplication.class, args);
				
	}

}
