package com.weather.utils;
/**
 * Utility class for validation methods.
 */
public class ValidationUtil {

    /**
     * Checks if the given string is null or empty.
     * @param str The string to check.
     * @return true if the string is invalid, false otherwise.
     */
    public static boolean isInvalidString(String str) {
        return (str == null || str.trim().isEmpty());
    }

    /**
     * Validates the city name based on specific criteria.
     * @param cityName The name of the city to validate.
     * @return true if the city name is invalid (does not match the required pattern),
     *         false if the city name is valid.
     */
    public static boolean isInvalidCity(String cityName) {
        String regex = "^[a-zA-Z\\s]{2,30}$"; // Allows alphabets and spaces, with length between 2 and 30
        return !cityName.matches(regex); // Returns true if regex does not match
    }

    /**
     * Validates if the latitude is within the valid range (-90 to 90).
     * @param latitude The latitude to check.
     * @return true if invalid (out of range or not a number), false otherwise.
     */
    public static boolean isInvalidLatitude(String latitude) {
        try {
            double lat = Double.parseDouble(latitude);
            return lat < -90 || lat > 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if the longitude is within the valid range (-180 to 180).
     * @param longitude The longitude to check.
     * @return true if invalid (out of range or not a number), false otherwise.
     */
    public static boolean isInvalidLongitude(String longitude) {
        try {
            double lon = Double.parseDouble(longitude);
            return lon < -180 || lon > 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
