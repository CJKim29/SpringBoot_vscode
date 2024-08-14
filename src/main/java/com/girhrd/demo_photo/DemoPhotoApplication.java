package com.girhrd.demo_photo;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan(basePackages = "com.girhrd.demo_photo", annotationClass = Mapper.class)
public class DemoPhotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoPhotoApplication.class, args);
	}

}
