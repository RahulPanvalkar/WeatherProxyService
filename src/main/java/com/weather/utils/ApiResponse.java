package com.weather.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public class ApiResponse {

    private boolean success;
    private String message;
    private Map<String, Object> data; // Generic type for flexibility
    private ErrorDetails error;

    // Default constructor
    public ApiResponse() {
    }

    // Constructor for success responses
    public ApiResponse(boolean success, String message, String dataJson) {
        this.success = success;
        this.message = message;
        this.data = convertToMap(dataJson);
    }

    private Map<String, Object> convertToMap(String dataJson) {
        Gson gson = new Gson(); // Use Gson library to parse JSON
        return gson.fromJson(dataJson, Map.class); // Convert JSON string to Map
    }


    // Constructor for error responses
    public ApiResponse(boolean success, String message, ErrorDetails error) {
        this.success = success;
        this.message = message;
        this.error = error;
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
        this.data = convertToMap(data);
    }

    public ErrorDetails getError() {
        return error;
    }

    public void setError(ErrorDetails error) {
        this.error = error;
    }

    // Class for error details
    public static class ErrorDetails {
        private int code;
        private String details;

        public ErrorDetails() {
        }

        public ErrorDetails(int code, String details) {
            this.code = code;
            this.details = details;
        }

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

        @Override
        public String toString() {
            return "ErrorDetails{" +
                    "code=" + code +
                    ", details='" + details + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
