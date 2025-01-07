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
        return (str == null || str.isEmpty());
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
