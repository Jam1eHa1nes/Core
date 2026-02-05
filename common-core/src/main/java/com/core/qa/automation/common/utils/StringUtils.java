package com.core.qa.automation.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Utility class for common string operations, including random string generation and null/empty checks.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     String email = StringUtils.randomEmail();
 *     boolean empty = StringUtils.isNullOrEmpty("");
 * </pre>
 */
public class StringUtils {

    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String ALPHANUMERIC = ALPHA + NUMERIC;
    private static final Random RANDOM = new Random();

    private StringUtils() {
        // Utility class
    }

    /**
     * Generates a random alphabetic string of the specified length.
     *
     * @param length the length of the string
     * @return a random alphabetic string
     */
    public static String randomAlphabetic(int length) {
        return randomString(ALPHA, length);
    }

    /**
     * Generates a random numeric string of the specified length.
     *
     * @param length the length of the string
     * @return a random numeric string
     */
    public static String randomNumeric(int length) {
        return randomString(NUMERIC, length);
    }

    /**
     * Generates a random alphanumeric string of the specified length.
     *
     * @param length the length of the string
     * @return a random alphanumeric string
     */
    public static String randomAlphaNumeric(int length) {
        return randomString(ALPHANUMERIC, length);
    }

    private static String randomString(String characters, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(RANDOM.nextInt(characters.length())));
        }
        return sb.toString();
    }

    /**
     * Generates a random email address.
     *
     * @return a random email address
     */
    public static String randomEmail() {
        return randomAlphaNumeric(6) + "@" + randomAlphaNumeric(6) + ".com";
    }

    /**
     * Generates an automatic reference string with timestamp.
     *
     * @return an auto-generated reference
     */
    public static String generateAutoRef() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("ssSSS");
        return "AUTO-Ref-" + dateFormat.format(date);
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param string the string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Checks if a list is null or empty.
     *
     * @param list the list to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * Checks if a string is not null and not empty.
     *
     * @param string the string to check
     * @return true if not null and not empty, false otherwise
     */
    public static boolean isNotNullOrEmpty(String string) {
        return !isNullOrEmpty(string);
    }

    /**
     * Trims a string safely, returning empty string if null.
     *
     * @param string the string to trim
     * @return the trimmed string or empty string if null
     */
    public static String safeTrim(String string) {
        return string == null ? "" : string.trim();
    }

    /**
     * Checks if two strings are equal, handling nulls safely.
     *
     * @param str1 first string
     * @param str2 second string
     * @return true if equal (including both null), false otherwise
     */
    public static boolean safeEquals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equals(str2);
    }
}

