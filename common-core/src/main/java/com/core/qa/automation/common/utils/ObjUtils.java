package com.core.qa.automation.common.utils;

import com.core.qa.automation.common.exception.AutomationException;

/**
 * Utility class for common object operations.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     boolean isNull = ObjUtils.isNull(null);
 *     ObjUtils.throwOnNull(myObject);
 * </pre>
 */
public class ObjUtils {

    private ObjUtils() {
        // Utility class
    }

    /**
     * Checks if an object is null.
     *
     * @param obj the object to check
     * @return true if null, false otherwise
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Checks if an object is not null.
     *
     * @param obj the object to check
     * @return true if not null, false otherwise
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * Throws an exception if the object is null.
     *
     * @param obj the object to check
     * @throws AutomationException if the object is null
     */
    public static void throwOnNull(Object obj) {
        if (obj == null) {
            throw new AutomationException("Null Object");
        }
    }

    /**
     * Throws an exception with a custom message if the object is null.
     *
     * @param obj     the object to check
     * @param message the exception message
     * @throws AutomationException if the object is null
     */
    public static void throwOnNull(Object obj, String message) {
        if (obj == null) {
            throw new AutomationException(message);
        }
    }

    /**
     * Returns the first non-null value from the arguments.
     *
     * @param values the values to check
     * @param <T>    the type of values
     * @return the first non-null value, or null if all are null
     */
    @SafeVarargs
    public static <T> T coalesce(T... values) {
        for (T value : values) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}

