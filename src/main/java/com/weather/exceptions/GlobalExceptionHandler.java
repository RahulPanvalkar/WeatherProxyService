package com.weather.exceptions;

import com.weather.utils.ApiResponse;
import com.weather.utils.LoggerUtil;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * This class is a global exception handler for the entire application.
 * It catches all exceptions thrown within the application and handles them by providing a structured error response.
 * This class uses @RestControllerAdvice to apply globally to all @RequestMapping methods in controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Logger to log the exception details
    private static final Logger logger = LoggerUtil.getLogger(GlobalExceptionHandler.class);

    /**
     * This method handles all general exceptions and returns a standardized error response.
     * @param ex The exception that was thrown.
     * @return A ResponseEntity containing an ApiResponse with error details and an INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        // Log the error message and exception stack trace for debugging
        logger.error("Unexpected error: {}", ex.getMessage(), ex);

        String msg = "Internal Server Error";
        // Create an ErrorDetails object to hold the error information
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);

        // Create the ApiResponse object with the success flag set to false and the error details
        msg = "An unexpected error occurred, please try again later.";
        ApiResponse response = new ApiResponse(false, msg, errorDetails);

        // Return a ResponseEntity with the INTERNAL_SERVER_ERROR status and the error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

