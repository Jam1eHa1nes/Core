package com.playwright.qa.automation.core;

/**
 * Custom exception for Playwright automation framework.
 */
public class PlaywrightCoreException extends RuntimeException {
    public PlaywrightCoreException(String message) {
        super(message);
    }

    public PlaywrightCoreException(String message, Throwable cause) {
        super(message, cause);
    }
}

