package com.conserveit.controlbg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.conserveit.controlbg.mapper")
public class ControlbgApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlbgApplication.class, args);
	}

}
