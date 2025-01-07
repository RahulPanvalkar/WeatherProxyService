package com.weather.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    /**
     * Retrieves a Logger instance for the specified class.
     * <p>
     * This utility method is used to avoid directly using
     * the LoggerFactory in multiple places in the code, ensuring consistency in logger management.
     * </p>
     * @param clazz The class for which the logger is to be created.
     * @return A Logger instance for the specified class.
     */
    public static Logger getLogger(Class<?> clazz) {
        // Return a logger for the given class using the LoggerFactory
        return LoggerFactory.getLogger(clazz);
    }
}
