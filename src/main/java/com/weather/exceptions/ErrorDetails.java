package com.weather.exceptions;

import java.time.LocalDateTime;

/**
 * This class represents the error details to be included in an API response.
 * It encapsulates the error code, a description of the error and a timestamp for when the error occurred.
 */
public class ErrorDetails {

    // The error code representing the type of error (e.g., 400 for bad request, 500 for server error).
    private int code;

    // A detailed message describing the error.
    private String details;

    // The timestamp when the error occurred.
    private LocalDateTime timeStamp;

    // Default constructor
    public ErrorDetails() {
    }

    /**
     * Constructor for creating an ErrorDetails instance with the provided error code and details.
     * The timestamp is automatically set to the current time when the error occurs.
     */
    public ErrorDetails(int code, String details) {
        this.code = code;
        this.details = details;
        this.timeStamp = LocalDateTime.now();  // Capture the current time for when the error occurred
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Provides a string representation of the ErrorDetails object.
     */
    @Override
    public String toString() {
        return String.format("ErrorDetails: {code=%d, details='%s', timeStamp='%s'}", code, details, timeStamp);
    }
}
