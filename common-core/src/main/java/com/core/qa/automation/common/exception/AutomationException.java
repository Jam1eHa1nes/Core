package com.core.qa.automation.common.exception;

/**
 * Common exception class for the automation framework.
 * Used for reporting automation-related errors across all modules.
 */
public class AutomationException extends RuntimeException {

    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutomationException(Throwable cause) {
        super(cause);
    }
}

