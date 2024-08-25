package com.forecast;

import com.util.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForecastService {

    public String getForecastData(String location) throws Exception {
        String apiUrl = Constants.BASE_URL + "/forecast.json?q="+location+"&days=3";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Add the required headers before making the request
        conn.setRequestProperty(Constants.API_HOST_HEADER_NAME, Constants.API_HOST_HEADER_VALUE);
        conn.setRequestProperty(Constants.API_KEY_HEADER_NAME, Constants.API_KEY_HEADER_VALUE);

        // Check the response code
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode == 200) { // OK
            // Read the response from the input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            throw new Exception("Failed to get forecast data. HTTP response code: " + responseCode);
        }
    }
}
