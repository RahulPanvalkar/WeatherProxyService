package com.weather;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.util.Constants;

public class WeatherService {

    public String getWeatherData(String location) throws Exception {
        // Encode the location to handle spaces and special characters
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
        String apiUrl = Constants.BASE_URL + "/current.json?q=" + encodedLocation;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header(Constants.API_HOST_HEADER_NAME, Constants.API_HOST_HEADER_VALUE)
                .header(Constants.API_KEY_HEADER_NAME, Constants.API_KEY_HEADER_VALUE)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Failed to get weather data. HTTP response code: " + response.statusCode());
        }

    }

}
