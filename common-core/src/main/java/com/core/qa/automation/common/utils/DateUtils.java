package com.core.qa.automation.common.utils;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for date operations, such as comparing dates and calculating time differences.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     boolean same = DateUtils.compareDates(date1, date2);
 *     long seconds = DateUtils.diffSeconds(date1, date2);
 * </pre>
 */
public class DateUtils {

    private DateUtils() {
        // Utility class
    }

    /**
     * Compares two dates for equality.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return true if dates are equal, false otherwise
     */
    public static boolean compareDates(Date date1, Date date2) {
        return date1.equals(date2);
    }

    /**
     * Calculates the difference in seconds between two dates.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return the difference in seconds
     */
    public static long diffSeconds(Date date1, Date date2) {
        return ChronoUnit.SECONDS.between(
                date1.toInstant(),
                date2.toInstant()
        );
    }

    /**
     * Calculates the difference in seconds between a date and now.
     *
     * @param date the date to compare
     * @return the difference in seconds from now
     */
    public static long diffSeconds(Date date) {
        return diffSeconds(date, new Date());
    }

    /**
     * Calculates the difference in minutes between a date and now.
     *
     * @param date the date to compare
     * @return the difference in minutes from now
     */
    public static long diffMinutes(Date date) {
        return ChronoUnit.MINUTES.between(
                date.toInstant(),
                new Date().toInstant()
        );
    }

    /**
     * Calculates the difference in hours between two dates.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return the difference in hours
     */
    public static long diffHours(Date date1, Date date2) {
        return ChronoUnit.HOURS.between(
                date1.toInstant(),
                date2.toInstant()
        );
    }

    /**
     * Calculates the difference in days between two dates.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return the difference in days
     */
    public static long diffDays(Date date1, Date date2) {
        return ChronoUnit.DAYS.between(
                date1.toInstant(),
                date2.toInstant()
        );
    }

    /**
     * Converts a Date to LocalDateTime.
     *
     * @param date the date to convert
     * @return the LocalDateTime representation
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Converts a Date to LocalDate.
     *
     * @param date the date to convert
     * @return the LocalDate representation
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Converts a LocalDateTime to Date.
     *
     * @param localDateTime the LocalDateTime to convert
     * @return the Date representation
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}

