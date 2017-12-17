package com.waterworks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.waterworks.repository.mapper")
@EnableCaching
public class WatersupplyplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatersupplyplatformApplication.class, args);
	}
}
