package com.weather.utils;
/**
 * This class contains constant values used throughout the application.
 * NOTE : API_KEY_HEADER_VALUE is stored in an environment variable to avoid exposing the API key in the source code.
 */
public class Constant {

    // The base URL for the weather API service.
    public static final String BASE_URL = "https://weatherapi-com.p.rapidapi.com";

    // The name of the header used to specify the host for the API request.
    public static final String API_HOST_HEADER_NAME = "X-RapidAPI-Host";

    // The value of the header used to specify the host for the API request.
    public static final String API_HOST_HEADER_VALUE = "weatherapi-com.p.rapidapi.com";

    // The name of the header used to specify the API key for the request.
    public static final String API_KEY_HEADER_NAME = "X-RapidAPI-Key";
}
