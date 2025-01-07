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

/**
 * REST controller to handle weather data requests.
 * Provides endpoints to fetch current weather data based on city name or geographical coordinates.
 */
@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private static final Logger logger = LoggerUtil.getLogger(WeatherController.class);

    /**
     * Constructor for WeatherController.
     * Injects the WeatherService dependency.
     * @param weatherService the service responsible for fetching current weather data.
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Retrieves current weather data for a specific city.
     * @param name the name of the city for which the weather data is requested.
     * @return a ResponseEntity containing the weather data or an error response in case of failure.
     *         - HTTP 200 OK: If the weather data is retrieved successfully.
     *         - HTTP Error Status: Corresponding to the error in case of failure.
     */
    @GetMapping("/city")
    public ResponseEntity<?> getWeatherByCity(@RequestParam String name) {
        logger.info("Fetching weather data for city: [{}]", name);

        // Fetch the weather data for the given city name
        ApiResponse response = weatherService.getWeatherByCity(name);
        logger.debug("Response received: {}", response);

        // Handle and return the response based on success or failure
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getErrorDetails().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Retrieves current weather data based on geographical coordinates.
     * @param latitude  the latitude of the location.
     * @param longitude the longitude of the location.
     * @return a ResponseEntity containing the weather data or an error response in case of failure.
     *         - HTTP 200 OK: If the weather data is retrieved successfully.
     *         - HTTP Error Status: Corresponding to the error in case of failure.
     */
    @GetMapping("/coordinates")
    public ResponseEntity<?> getWeatherByCoordinates(@RequestParam("lat") String latitude, @RequestParam("lon") String longitude) {
        logger.info("Fetching weather data for coordinates: [{}, {}]", latitude, longitude);

        // Fetch the weather data for the given geographical coordinates
        ApiResponse response = weatherService.getWeatherByCoordinates(latitude, longitude);
        logger.debug("Response received: {}", response);

        // Handle and return the response based on success or failure
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getErrorDetails().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
