package com.core.qa.automation.common.logger;

import com.core.qa.automation.common.utils.Colours;

/**
 * Logger facade for the automation framework.
 * Provides a simple interface for logging operations.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     Logger logger = new Logger();
 *     logger.log("Action", "Details", "Additional info");
 *     logger.warn("Warning message");
 * </pre>
 */
public class Logger implements LoggerInterface {

    private final LoggerImpl loggerImpl = LoggerImpl.getInstance();

    @Override
    public void log(String... args) {
        loggerImpl.log(args);
    }

    @Override
    public void log(Colours colour, String text) {
        loggerImpl.log(colour, text);
    }

    @Override
    public void warn(String text) {
        loggerImpl.warn(text);
    }

    @Override
    public void error(String text) {
        loggerImpl.error(text);
    }

    @Override
    public void debug(String text) {
        loggerImpl.debug(text);
    }
}

