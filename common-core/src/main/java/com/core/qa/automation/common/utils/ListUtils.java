package com.core.qa.automation.common.utils;

import com.core.qa.automation.common.exception.AutomationException;

import java.util.List;

/**
 * Utility class for common list operations.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     boolean empty = ListUtils.isNullOrEmpty(myList);
 *     double sum = ListUtils.aggregate(numericList);
 * </pre>
 */
public class ListUtils {

    private ListUtils() {
        // Utility class
    }

    /**
     * Checks if a list is null or empty.
     *
     * @param list the list to check
     * @return true if the list is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Checks if a list is not null and not empty.
     *
     * @param list the list to check
     * @return true if the list is not null and not empty, false otherwise
     */
    public static boolean isNotNullOrEmpty(List<?> list) {
        return !isNullOrEmpty(list);
    }

    /**
     * Aggregates a list of strings containing numeric data.
     *
     * @param list the list of numeric strings
     * @return the sum as a double
     * @throws AutomationException if the list is null/empty or contains non-numeric values
     */
    public static double aggregate(List<String> list) {
        if (isNullOrEmpty(list)) {
            throw new AutomationException("Cannot aggregate null or empty list");
        }
        double result = 0.0;
        try {
            for (String entry : list) {
                if (StringUtils.isNullOrEmpty(entry)) {
                    continue;
                }
                result += Double.parseDouble(entry.trim());
            }
        } catch (NumberFormatException nfe) {
            throw new AutomationException("Unable to aggregate - non-numeric value found", nfe);
        }
        return result;
    }

    /**
     * Gets the first element from a list safely.
     *
     * @param list the list
     * @param <T>  the type of elements
     * @return the first element, or null if list is empty
     */
    public static <T> T getFirst(List<T> list) {
        return isNullOrEmpty(list) ? null : list.get(0);
    }

    /**
     * Gets the last element from a list safely.
     *
     * @param list the list
     * @param <T>  the type of elements
     * @return the last element, or null if list is empty
     */
    public static <T> T getLast(List<T> list) {
        return isNullOrEmpty(list) ? null : list.get(list.size() - 1);
    }

    /**
     * Gets an element from a list safely by index.
     *
     * @param list  the list
     * @param index the index (0-based)
     * @param <T>   the type of elements
     * @return the element at index, or null if out of bounds
     */
    public static <T> T getAt(List<T> list, int index) {
        if (isNullOrEmpty(list) || index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }
}

