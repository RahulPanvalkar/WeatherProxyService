package com.weather.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String timestamp;

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
        this.timestamp = getTimestamp();  // Capture the current time for when the error occurred
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

    public String getTimeStamp() {
        return timestamp;
    }

    /**
     * Provides a string representation of the ErrorDetails object.
     */
    @Override
    public String toString() {
        return String.format("ErrorDetails: {code=%d, details='%s', timeStamp='%s'}", code, details, timestamp);
    }

    // Method to get the current timestamp in a custom format
    private String getTimestamp() {
        // Get the current datetime
        LocalDateTime dateTime = LocalDateTime.now();

        // Create a DateTimeFormatter with a custom pattern to format the date-time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

        // Format the LocalDateTime using the DateTimeFormatter and return it as a string
        return dateTime.format(formatter);
    }
}
