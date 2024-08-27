package com.forecast;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        // Set CORS headers
        setCorsHeaders(response);

        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String location = request.getParameter("location");
        System.out.println("location :: " + location);
        if (location == null || location.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Location");
        } else {
            try {
                String forecastData = forecastService.getForecastData(location);
                System.out.println("Forecast Data :: " + forecastData);
                response.setContentType("application/json");
                response.getWriter().write(forecastData);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching weather data");
            }
        }

    }

    private void setCorsHeaders(HttpServletResponse response) {
        //response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Origin", "http://52.203.12.227");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
