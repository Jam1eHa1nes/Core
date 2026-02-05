package com.core.qa.automation.common;

import com.core.qa.automation.common.logger.Logger;

/**
 * Utility class for accessing and managing JVM arguments related to automation.
 * This class is shared across all modules and determines which framework to use.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     if (VMArgs.isSelenium()) {
 *         // Use Selenium
 *     }
 *     if (VMArgs.headless) {
 *         // Run browser in headless mode
 *     }
 * </pre>
 * <p>
 * <b>System Properties:</b>
 * <ul>
 *   <li>automation.framework - "selenium" (default) or "playwright"</li>
 *   <li>headless.mode - "true" or "false"</li>
 *   <li>browser.screenshot.onerror - "true" or "false"</li>
 *   <li>browser.keep.open - "true" or "false"</li>
 *   <li>browser.window - window index (1-based)</li>
 * </ul>
 */
public class VMArgs {

    /**
     * Enum representing the automation framework to use.
     */
    public enum Framework {
        SELENIUM,
        PLAYWRIGHT
    }

    public static final Framework framework;
    public static final boolean headless;
    public static final boolean screenshotOnError;
    public static final boolean browserKeepOpen;
    public static final int browserWindowIndex;

    static {
        Logger logger = new Logger();

        // Determine framework
        String frameworkProp = System.getProperty("automation.framework", "playwright").toLowerCase();
        if ("playwright".equals(frameworkProp)) {
            framework = Framework.PLAYWRIGHT;
        } else {
            framework = Framework.SELENIUM;
        }
        logger.log("Automation Framework: " + framework);

        // Browser settings
        headless = "true".equals(System.getProperty("headless.mode", "false"));
        screenshotOnError = "true".equals(System.getProperty("browser.screenshot.onerror", "true"));
        browserKeepOpen = "true".equals(System.getProperty("browser.keep.open", "false"));

        // Browser window index
        int windowIdx = 0;
        try {
            windowIdx = Integer.parseInt(System.getProperty("browser.window", "1")) - 1;
        } catch (NumberFormatException e) {
            logger.warn("Provided browser.window is not a number, defaulting to 0");
        }
        browserWindowIndex = windowIdx;
    }

    /**
     * Checks if Selenium framework is selected.
     *
     * @return true if Selenium
     */
    public static boolean isSelenium() {
        return framework == Framework.SELENIUM;
    }

    /**
     * Checks if Playwright framework is selected.
     *
     * @return true if Playwright
     */
    public static boolean isPlaywright() {
        return framework == Framework.PLAYWRIGHT;
    }

    /**
     * Gets the framework name as a string.
     *
     * @return "selenium" or "playwright"
     */
    public static String getFrameworkName() {
        return framework.name().toLowerCase();
    }
}

