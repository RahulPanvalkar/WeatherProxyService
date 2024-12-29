package com.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        setCorsHeaders(httpResponse);
        chain.doFilter(request, response);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        // Get the CORS origin from environment variables
        String allowedOrigin = Optional.ofNullable(System.getenv("CORS_ALLOWED_ORIGIN"))
                .orElseThrow(()->new IllegalArgumentException("CORS_ALLOWED_ORIGIN environment variable is not set or is invalid."));
        response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}

