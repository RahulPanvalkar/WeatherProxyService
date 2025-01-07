package com.weather.utils;

import com.google.gson.Gson;
import com.weather.exceptions.ErrorDetails;

import java.util.Map;

/**
 * Represents a standard response structure for API responses.
 * Contains success status, message, data, and error details.
 */
public class ApiResponse {

    private boolean success;
    private String message;
    private Map<String, Object> data;  // Data related to the response, stored as a map
    private ErrorDetails errorDetails;  // Error details if the operation failed

    // Default constructor
    public ApiResponse() {
    }

    /**
     * Constructor for success responses.
     * @param success  Indicates if the operation was successful.
     * @param message  Message related to the response.
     * @param dataJson JSON string representing the response data.
     */
    public ApiResponse(boolean success, String message, String dataJson) {
        this.success = success;
        this.message = message;
        this.data = convertToMap(dataJson);  // Converts the JSON string into a map
    }

    /**
     * Converts a JSON string to a map.
     * @param dataJson The JSON string to be converted.
     * @return The corresponding map of key-value pairs.
     */
    private Map<String, Object> convertToMap(String dataJson) {
        Gson gson = new Gson();  // Using Gson to parse the JSON
        return gson.fromJson(dataJson, Map.class);  // Converts JSON string to Map
    }

    /**
     * Constructor for error responses.
     * @param success      Indicates if the operation was successful (false for errors).
     * @param message      Message related to the error response.
     * @param errorDetails Details about the error.
     */
    public ApiResponse(boolean success, String message, ErrorDetails errorDetails) {
        this.success = success;
        this.message = message;
        this.errorDetails = errorDetails;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String data) {
        this.data = convertToMap(data);  // Sets data after converting the JSON string to Map
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(ErrorDetails errorDetails) {
        this.errorDetails = errorDetails;
    }

    /**
     * @return A string representation of the ApiResponse Object.
     */
    @Override
    public String toString() {
        return String.format("ApiResponse{success=%b, message='%s', data=%s, error=%s}",
                success, message, data, errorDetails);
    }
}
