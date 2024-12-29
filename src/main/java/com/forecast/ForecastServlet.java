package com.forecast;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forecast")
public class ForecastServlet extends HttpServlet {
    private ForecastService forecastService;

    @Override
    public void init() throws ServletException {
        forecastService = new ForecastService();
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
                String forecastData = forecastService.getForecastData(location);
                //System.out.println("Forecast Data :: " + forecastData);
                response.setContentType("application/json");
                response.getWriter().write(forecastData);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching weather data");
            }
        }
    }
}
