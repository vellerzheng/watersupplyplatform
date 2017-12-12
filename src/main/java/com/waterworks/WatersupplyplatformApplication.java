package com.waterworks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.waterworks.repository.mapper")
public class WatersupplyplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatersupplyplatformApplication.class, args);
	}
}
