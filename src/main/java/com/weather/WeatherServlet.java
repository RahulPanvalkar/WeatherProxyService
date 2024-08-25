package com.weather;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        // Set CORS headers
        setCorsHeaders(response);

        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String location = request.getParameter("location");
        System.out.println("location :: "+location);
        if (location == null || location.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Location");
        } else {
            try {
                String weatherData = weatherService.getWeatherData(location);
                System.out.println("Weather Data :: "+weatherData);
                response.setContentType("application/json");
                response.getWriter().write(weatherData);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching weather data");
            }
        }

    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://52.203.12.227");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }


}

