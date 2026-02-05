package com.core.qa.automation.common.logger;

import com.core.qa.automation.common.utils.Colours;

/**
 * Interface for logging operations in the automation framework.
 */
public interface LoggerInterface {

    /**
     * Logs one or more string arguments.
     *
     * @param args the strings to log
     */
    void log(String... args);

    /**
     * Logs a message with a specific color.
     *
     * @param colour the color to use
     * @param text   the text to log
     */
    void log(Colours colour, String text);

    /**
     * Logs a warning message.
     *
     * @param text the warning text
     */
    void warn(String text);

    /**
     * Logs an error message.
     *
     * @param text the error text
     */
    void error(String text);

    /**
     * Logs a debug message.
     *
     * @param text the debug text
     */
    void debug(String text);
}

