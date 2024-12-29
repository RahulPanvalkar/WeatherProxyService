package com.weather;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {
    private WeatherService weatherService;

    @Override
    public void init() throws ServletException {
        weatherService = new WeatherService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getting location
        String location = request.getParameter("location");
        System.out.println("location :: " + location);
        if (location == null || location.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Location");
        } else {
            try {
                String weatherData = weatherService.getWeatherData(location);
                //System.out.println("Weather Data :: " + weatherData);
                response.setContentType("application/json");
                response.getWriter().write(weatherData);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching weather data");
            }
        }

    }
}

