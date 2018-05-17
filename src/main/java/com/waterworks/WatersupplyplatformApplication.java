package com.waterworks;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@MapperScan("com.waterworks.repository.mapper")
@EnableCaching
public class WatersupplyplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatersupplyplatformApplication.class, args);
	}
}
