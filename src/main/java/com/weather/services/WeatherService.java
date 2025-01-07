package com.weather.services;

import com.weather.exceptions.ErrorDetails;
import com.weather.utils.ApiResponse;
import com.weather.utils.Constant;
import com.weather.utils.LoggerUtil;
import com.weather.utils.ValidationUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherService {

    private final String apiKeyHeaderValue;
    private static final Logger logger = LoggerUtil.getLogger(WeatherService.class);

    // Constructor to inject API key value from an environment variable
    public WeatherService(@Value("${API_KEY_HEADER_VALUE}") String apiKeyHeaderValue) {
        this.apiKeyHeaderValue = apiKeyHeaderValue;
    }

    /**
     * Fetches the current day's weather data for a given city.
     *
     * @param city The city name.
     * @return ApiResponse containing weather data or error details.
     */
    public ApiResponse getWeatherByCity(String city) {
        String msg = "";
        try {
            // Null or empty String Validation for city input
            if (ValidationUtil.isInvalidString(city)) {
                ErrorDetails errorDetails = new ErrorDetails(400, "City name is required.");
                msg = "City name cannot be blank";
                return new ApiResponse(false, msg, errorDetails);
            }

            // Validate city name
            if (ValidationUtil.isInvalidCity(city)) {
                ErrorDetails errorDetails = new ErrorDetails(400, "Invalid City name.");
                msg = "City name must contain only alphabets and spaces, up to 30 letters.";
                return new ApiResponse(false, msg, errorDetails);
            }

            // Encode the city name to handle special characters
            String encodedLocation = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String apiUrl = Constant.BASE_URL + "/current.json?q=" + encodedLocation;
            logger.info("apiURL : {}", apiUrl);
            // Send the request
            HttpResponse<String> response = sendHttpRequest(apiUrl);
            logger.info("status code : {}", response.statusCode());

            // Handle response
            if (response.statusCode() == 200) {
                return new ApiResponse(true, "Request successful.", response.body());
            } else if (response.statusCode() == 400) {
                ErrorDetails errorDetails = new ErrorDetails(400, "Location not found.");
                msg = "No matching location found for city : [" + city + "]";
                return new ApiResponse(false, msg, errorDetails);
            } else {
                ErrorDetails errorDetails = new ErrorDetails(500, "Failed to fetch weather data.");
                msg = "Internal server error, Please try again later.";
                return new ApiResponse(false, msg, errorDetails);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "An unexpected error occurred while fetching weather data.";
            ErrorDetails errorDetails = new ErrorDetails(500, msg);
            return new ApiResponse(false, "Exception occurred", errorDetails);
        }
    }

    /**
     * Fetches the weather data for current day using coordinates (latitude, longitude).
     *
     * @param latitude  The latitude value.
     * @param longitude The longitude value.
     * @return ApiResponse containing weather data or error details.
     */
    public ApiResponse getWeatherByCoordinates(String latitude, String longitude) {
        String msg = "";
        try {
            // Validate coordinates input
            if (ValidationUtil.isInvalidString(latitude) || ValidationUtil.isInvalidString(longitude)) {
                ErrorDetails errorDetails = new ErrorDetails(400, "Invalid Co-ordinates.");
                msg = "Coordinates are required.";
                return new ApiResponse(false, msg, errorDetails);
            }

            // Validate the range of latitude and longitude
            if (ValidationUtil.isInvalidLatitude(latitude) || ValidationUtil.isInvalidLongitude(longitude)) {
                ErrorDetails errorDetails = new ErrorDetails(400, "Invalid coordinates.");
                msg = "Invalid coordinates : [" + latitude + "," + longitude + "]";
                return new ApiResponse(false, msg, errorDetails);
            }

            // Build the API URL
            String encodedLatitude = URLEncoder.encode(latitude, StandardCharsets.UTF_8);
            String encodedLongitude = URLEncoder.encode(longitude, StandardCharsets.UTF_8);
            String apiUrl = Constant.BASE_URL + "/current.json?q=" + encodedLatitude + "," + encodedLongitude;
            logger.info("apiURL : {}", apiUrl);
            // Send the request
            HttpResponse<String> response = sendHttpRequest(apiUrl);
            logger.info("status code : {}", response.statusCode());

            // Handle response
            if (response.statusCode() == 200) {
                return new ApiResponse(true, "Request successful.", response.body());
            } else if (response.statusCode() == 400) {
                ErrorDetails errorDetails = new ErrorDetails(400, "Location not found.");
                msg = "No matching location found for coordinates : [" + latitude + "," + longitude + "]";
                return new ApiResponse(false, msg, errorDetails);
            } else {
                ErrorDetails errorDetails = new ErrorDetails(500, "Failed to fetch weather data.");
                msg = "Internal server error, Please try again later.";
                return new ApiResponse(false, msg, errorDetails);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg = "An unexpected error occurred while fetching weather data.";
            ErrorDetails errorDetails = new ErrorDetails(500, msg);
            return new ApiResponse(false, "Exception occurred", errorDetails);
        }
    }

    /**
     * Method to build and Send an HTTP request to the weather API.
     *
     * @param apiUrl The URL to send the request to.
     * @return The response from the API.
     * @throws IOException If an error occurs while sending the request.
     * @throws InterruptedException If the request is interrupted.
     */
    private HttpResponse<String> sendHttpRequest(String apiUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header(Constant.API_HOST_HEADER_NAME, Constant.API_HOST_HEADER_VALUE)
                .header(Constant.API_KEY_HEADER_NAME, apiKeyHeaderValue)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}