package com.weather.utils;

public class ValidationUtil {

    public static boolean isInvalidString(String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isInvalidLatitude(String latitude) {
        try {
            double lat = Double.parseDouble(latitude);
            return lat < -90 || lat > 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInvalidLongitude(String longitude) {
        try {
            double lon = Double.parseDouble(longitude);
            return lon < -180 || lon > 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
