package com.weather.filters;

import com.weather.utils.LoggerUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CorsFilter implements Filter {

    private static final Logger logger = LoggerUtil.getLogger(CorsFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String allowedOrigin = Optional.ofNullable(System.getenv("CORS_ALLOWED_ORIGIN"))
                .orElseThrow(() -> new IllegalArgumentException("CORS_ALLOWED_ORIGIN environment variable is not set or is invalid."));

        httpResponse.setHeader("Access-Control-Allow-Origin", allowedOrigin);
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        String reqOrigin = httpRequest.getHeader("Origin");
        logger.debug("Allowed Origin : {}", allowedOrigin);
        logger.debug("Request Origin : {}", reqOrigin);

        if (reqOrigin != null && !allowedOrigin.equals(reqOrigin)) {
            logger.info("Different origin request.. returning error!");
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);
    }

}