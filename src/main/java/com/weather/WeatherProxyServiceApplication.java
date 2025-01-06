package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.weather")
public class WeatherProxyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherProxyServiceApplication.class, args);
	}

}
