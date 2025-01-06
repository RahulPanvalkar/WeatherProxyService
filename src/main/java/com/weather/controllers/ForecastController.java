package com.weather.controllers;

import com.weather.services.ForecastService;
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
@RequestMapping("/api/v1/forecast")
public class ForecastController {
    private final ForecastService forecastService;
    private static final Logger logger = LoggerUtil.getLogger(ForecastController.class);

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/city")
    public ResponseEntity<?> getForecastByCity(@RequestParam String name) {
        logger.info("city >> [{}]", name);

        ApiResponse response = forecastService.getForecastByCity(name);
        logger.debug("Response received: {}", response);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getError().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/coordinates")
    public ResponseEntity<?> getForecastByCoordinates(@RequestParam("lat") String latitude, @RequestParam("lon") String longitude) {
        logger.info("co-ordinates >> [{}, {}]", latitude, longitude);

        ApiResponse response = forecastService.getForecastByCoordinates(latitude, longitude);
        logger.debug("Response received: {}", response);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getError().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
