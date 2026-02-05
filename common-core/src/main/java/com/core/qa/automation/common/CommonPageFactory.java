package com.core.qa.automation.common;

import com.core.qa.automation.common.exception.AutomationException;
import com.core.qa.automation.common.logger.Logger;

/**
 * Factory class for creating CommonPageInterface instances.
 * Uses VMArgs to determine which framework implementation to instantiate.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     // Set framework via system property
 *     // -Dautomation.framework=selenium
 *     // or
 *     // -Dautomation.framework=playwright
 *     
 *     CommonPageInterface page = CommonPageFactory.create();
 *     page.open();
 *     page.go("https://example.com");
 * </pre>
 */
public class CommonPageFactory {

    private static final Logger logger = new Logger();
    private static CommonPageInterface instance;

    private static final String SELENIUM_CLASS = "com.selenium.qa.automation.core.CommonPage";
    private static final String PLAYWRIGHT_CLASS = "com.playwright.qa.automation.core.CommonPage";

    private CommonPageFactory() {
        // Factory class
    }

    /**
     * Creates or returns the singleton CommonPageInterface instance.
     * The implementation is determined by the automation.framework system property.
     *
     * @return CommonPageInterface implementation
     * @throws AutomationException if the implementation cannot be instantiated
     */
    public static synchronized CommonPageInterface create() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    /**
     * Creates a new CommonPageInterface instance (non-singleton).
     * Useful when you need multiple browser instances.
     *
     * @return new CommonPageInterface implementation
     * @throws AutomationException if the implementation cannot be instantiated
     */
    public static CommonPageInterface createNew() {
        return createInstance();
    }

    /**
     * Resets the singleton instance.
     * Call this when you need to create a fresh browser session.
     */
    public static synchronized void reset() {
        instance = null;
    }

    private static CommonPageInterface createInstance() {
        String className;
        
        if (VMArgs.isPlaywright()) {
            className = PLAYWRIGHT_CLASS;
            logger.log("Creating Playwright CommonPage instance");
        } else {
            className = SELENIUM_CLASS;
            logger.log("Creating Selenium CommonPage instance");
        }

        try {
            Class<?> clazz = Class.forName(className);
            
            // Try to get singleton instance via getInstance() method
            try {
                java.lang.reflect.Method getInstance = clazz.getMethod("getInstance");
                return (CommonPageInterface) getInstance.invoke(null);
            } catch (NoSuchMethodException e) {
                // No getInstance method, create new instance
                return (CommonPageInterface) clazz.getDeclaredConstructor().newInstance();
            }
        } catch (ClassNotFoundException e) {
            throw new AutomationException(
                    "Framework class not found: " + className + 
                    ". Make sure the correct module is on the classpath. " +
                    "Use -Dautomation.framework=selenium or -Dautomation.framework=playwright", e);
        } catch (Exception e) {
            throw new AutomationException("Failed to create CommonPage instance: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the current framework being used.
     *
     * @return the framework enum value
     */
    public static VMArgs.Framework getFramework() {
        return VMArgs.framework;
    }
}

