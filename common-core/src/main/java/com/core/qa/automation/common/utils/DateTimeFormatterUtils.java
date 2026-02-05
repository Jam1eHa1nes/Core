package com.core.qa.automation.common.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Utility class for common {@link java.time.format.DateTimeFormatter} patterns.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     String formatted = DateTimeFormatterUtils.OUTPUT_FORMATTER.format(LocalDateTime.now());
 * </pre>
 */
public class DateTimeFormatterUtils {

    private DateTimeFormatterUtils() {
        // Utility class
    }

    /**
     * ISO 8601 format with optional milliseconds: yyyy-MM-dd'T'HH:mm:ss.SSSX
     */
    public static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
            .optionalEnd()
            .appendPattern("X")
            .toFormatter();

    /**
     * Human-readable format: dd MMM yyyy HH:mm:ss
     */
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");

    /**
     * Date only format: dd MMM yyyy
     */
    public static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Simple date format: yyyy-MM-dd
     */
    public static final DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Time only format: HH:mm:ss
     */
    public static final DateTimeFormatter TIME_ONLY_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * UK date format: dd/MM/yyyy
     */
    public static final DateTimeFormatter UK_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * US date format: MM/dd/yyyy
     */
    public static final DateTimeFormatter US_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Timestamp format: yyyyMMdd_HHmmss
     */
    public static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
}

