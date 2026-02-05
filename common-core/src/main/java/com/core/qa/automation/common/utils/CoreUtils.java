package com.core.qa.automation.common.utils;

import com.core.qa.automation.common.exception.AutomationException;

import java.util.List;

/**
 * Core utility class for common validation operations.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     CoreUtils.throwOnZeroOrMinus(index);
 *     CoreUtils.throwOnNullOrEmpty(myString);
 * </pre>
 */
public class CoreUtils {

    private CoreUtils() {
        // Utility class
    }

    /**
     * Throws an exception if any index is zero or negative.
     *
     * @param indexes the indexes to check
     * @throws AutomationException if any index is <= 0
     */
    public static void throwOnZeroOrMinus(int... indexes) {
        for (int i : indexes) {
            if (i <= 0) {
                throw new AutomationException("Invalid Index: " + i);
            }
        }
    }

    /**
     * Throws an exception if the index is zero or negative.
     *
     * @param index the index to check
     * @throws AutomationException if index is <= 0
     */
    public static void throwOnZeroOrMinus(int index) {
        if (index <= 0) {
            throw new AutomationException("Invalid Index: " + index);
        }
    }

    /**
     * Throws an exception if the string is null or empty.
     *
     * @param s the string to check
     * @throws AutomationException if string is null or empty
     */
    public static void throwOnNullOrEmpty(String s) {
        if (StringUtils.isNullOrEmpty(s)) {
            throw new AutomationException("Empty or null String");
        }
    }

    /**
     * Throws an exception if the list is null or empty.
     *
     * @param list the list to check
     * @throws AutomationException if list is null or empty
     */
    public static void throwOnNullOrEmpty(List<?> list) {
        if (ListUtils.isNullOrEmpty(list)) {
            throw new AutomationException("Empty or null List");
        }
    }

    /**
     * Throws an AutomationException with the given message.
     *
     * @param message the exception message
     * @throws AutomationException always
     */
    public static void fling(String message) {
        throw new AutomationException(message);
    }

    /**
     * Throws an AutomationException with the given message if the condition is true.
     *
     * @param condition the condition to check
     * @param message   the exception message
     * @throws AutomationException if condition is true
     */
    public static void flingIf(boolean condition, String message) {
        if (condition) {
            throw new AutomationException(message);
        }
    }
}

