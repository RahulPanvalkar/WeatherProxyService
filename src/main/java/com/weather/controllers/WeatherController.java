package com.weather.controllers;

import com.weather.services.WeatherService;
import com.weather.utils.ApiResponse;
import com.weather.utils.LoggerUtil;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private static final Logger logger = LoggerUtil.getLogger(WeatherController.class);

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/city")
    public ResponseEntity<?> getWeatherByCity(@RequestParam String name) {
        logger.info("city >> [{}]", name);

        ApiResponse response = weatherService.getWeatherByCity(name);
        logger.debug("Response received: {}", response);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getError().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/coordinates")
    public ResponseEntity<?> getWeatherByCoordinates(@RequestParam("lat") String latitude, @RequestParam("lon") String longitude) {
        logger.info("co-ordinates >> [{}, {}]", latitude, longitude);

        ApiResponse response = weatherService.getWeatherByCoordinates(latitude, longitude);
        logger.debug("Response received: {}", response);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getError().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

