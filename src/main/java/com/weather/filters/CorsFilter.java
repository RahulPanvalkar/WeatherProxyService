package com.weather.filters;

import com.weather.utils.LoggerUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * A servlet filter for handling Cross-Origin Resource Sharing (CORS) requests.
 * Ensures that requests originate from an allowed origin and adds necessary CORS headers to responses.
 * NOTE : CORS_ALLOWED_ORIGIN is an environment variable that defines the allowed origin for cross-origin requests.
 */
@Component
public class CorsFilter implements Filter {

    private static final Logger logger = LoggerUtil.getLogger(CorsFilter.class);

    /**
     * Handles CORS for incoming HTTP requests.
     * Adds the appropriate CORS headers to the HTTP response and validates the request origin against the allowed origin.
     * @param request  the incoming HTTP request.
     * @param response the outgoing HTTP response.
     * @param chain    the filter chain to pass the request and response to the next filter.
     * @throws IOException      if an I/O error occurs during processing.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            // Fetch the allowed origin from an environment variable
            String allowedOrigin = Optional.ofNullable(System.getenv("CORS_ALLOWED_ORIGIN"))
                    .orElseThrow(() -> new IllegalArgumentException("CORS_ALLOWED_ORIGIN environment variable is not set or is invalid."));

            // Set CORS response headers
            httpResponse.setHeader("Access-Control-Allow-Origin", allowedOrigin);
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

            // Log the allowed origin and the origin of the incoming request
            String reqOrigin = httpRequest.getHeader("Origin");
            logger.debug("Allowed Origin : {}", allowedOrigin);
            logger.debug("Request Origin : {}", reqOrigin);

            // Validate the request origin
            if (reqOrigin != null && !allowedOrigin.equals(reqOrigin)) {
                logger.warn("Unauthorized origin detected: {}", reqOrigin);
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Pass the request and response to the next filter in the chain
            chain.doFilter(request, response);

        } catch (IllegalArgumentException e) {
            logger.error("Configuration error: {}", e.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "CORS configuration error.");

        } catch (Exception e) {
            logger.error("Unexpected error occurred in CORS filter: {}", e.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }

}
