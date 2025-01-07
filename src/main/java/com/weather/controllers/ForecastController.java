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

/**
 * REST controller to handle weather forecast requests.
 * Provides endpoints to fetch weather forecasts based on city name or geographical coordinates.
 */
@RestController
@RequestMapping("/api/v1/forecast")
public class ForecastController {
    private final ForecastService forecastService;
    private static final Logger logger = LoggerUtil.getLogger(ForecastController.class);

    /**
     * Constructor to Inject the ForecastService dependency.
     * @param forecastService the service responsible for fetching weather forecast data.
     */
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    /**
     * Retrieves the weather forecast for a specific city.
     * @param name the name of the city for which the forecast is requested.
     * @return a ResponseEntity containing the forecast data or an error response in case of failure.
     *         - HTTP 200 OK: If the forecast is retrieved successfully.
     *         - HTTP Error Status: Corresponding to the error in case of failure.
     */
    @GetMapping("/city")
    public ResponseEntity<?> getForecastByCity(@RequestParam String name) {
        logger.info("Fetching forecast for city: [{}]", name);

        // Fetch the forecast data for the given city name
        ApiResponse response = forecastService.getForecastByCity(name);
        //logger.debug("Response received: {}", response);

        // Handle and return the response based on success or failure
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getErrorDetails().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Retrieves the weather forecast based on geographical coordinates.
     * @param latitude  the latitude of the location.
     * @param longitude the longitude of the location.
     * @return a ResponseEntity containing the forecast data or an error response in case of failure.
     *         - HTTP 200 OK: If the forecast is retrieved successfully.
     *         - HTTP Error Status: Corresponding to the error in case of failure.
     */
    @GetMapping("/coordinates")
    public ResponseEntity<?> getForecastByCoordinates(@RequestParam("lat") String latitude, @RequestParam("lon") String longitude) {
        logger.info("Fetching forecast for coordinates: [{}, {}]", latitude, longitude);

        // Fetch the forecast data for the given geographical coordinates
        ApiResponse response = forecastService.getForecastByCoordinates(latitude, longitude);
        //logger.debug("Response received: {}", response);

        // Handle and return the response based on success or failure
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.valueOf(response.getErrorDetails().getCode())).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
