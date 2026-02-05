package com.core.qa.automation.common;

import java.util.ServiceLoader;

/**
 * Factory for creating PageActions instances.
 * Uses Java ServiceLoader to discover and instantiate the appropriate implementation
 * (Playwright or Selenium) based on what's available on the classpath.
 * <p>
 * <b>Configuration:</b>
 * <ul>
 *   <li>Set system property "automation.framework" to "playwright" or "selenium"</li>
 *   <li>Or set environment variable "AUTOMATION_FRAMEWORK"</li>
 *   <li>Or let it auto-detect based on classpath</li>
 * </ul>
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     // Auto-detect framework
 *     PageActions page = PageFactory.create();
 *
 *     // Specify framework explicitly
 *     PageActions page = PageFactory.create(Framework.PLAYWRIGHT);
 * </pre>
 */
public final class PageFactory {

    private static final String FRAMEWORK_PROPERTY = "automation.framework";
    private static final String FRAMEWORK_ENV = "AUTOMATION_FRAMEWORK";

    private PageFactory() {
        // Utility class
    }

    /**
     * Supported automation frameworks.
     */
    public enum Framework {
        PLAYWRIGHT,
        SELENIUM,
        AUTO
    }

    /**
     * Creates a PageActions instance using auto-detection.
     *
     * @return a new PageActions instance
     * @throws IllegalStateException if no implementation is found
     */
    public static PageActions create() {
        return create(Framework.AUTO);
    }

    /**
     * Creates a PageActions instance for the specified framework.
     *
     * @param framework the framework to use
     * @return a new PageActions instance
     * @throws IllegalStateException if the specified implementation is not found
     */
    public static PageActions create(Framework framework) {
        Framework resolved = resolveFramework(framework);

        ServiceLoader<PageActionsProvider> loader = ServiceLoader.load(PageActionsProvider.class);

        for (PageActionsProvider provider : loader) {
            if (resolved == Framework.AUTO || provider.getFramework() == resolved) {
                return provider.create();
            }
        }

        // Fallback: try to instantiate directly by class name
        return createByClassName(resolved);
    }

    /**
     * Resolves the framework to use based on configuration.
     */
    private static Framework resolveFramework(Framework requested) {
        if (requested != Framework.AUTO) {
            return requested;
        }

        // Check system property
        String frameworkProp = System.getProperty(FRAMEWORK_PROPERTY);
        if (frameworkProp != null && !frameworkProp.isEmpty()) {
            return parseFramework(frameworkProp);
        }

        // Check environment variable
        String frameworkEnv = System.getenv(FRAMEWORK_ENV);
        if (frameworkEnv != null && !frameworkEnv.isEmpty()) {
            return parseFramework(frameworkEnv);
        }

        return Framework.AUTO;
    }

    /**
     * Parses a framework string.
     */
    private static Framework parseFramework(String value) {
        try {
            return Framework.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (value.toLowerCase().contains("playwright")) {
                return Framework.PLAYWRIGHT;
            } else if (value.toLowerCase().contains("selenium")) {
                return Framework.SELENIUM;
            }
            return Framework.AUTO;
        }
    }

    /**
     * Attempts to create an instance by class name.
     */
    private static PageActions createByClassName(Framework framework) {
        String[] playwrightClasses = {
                "com.playwright.qa.automation.core.PlaywrightPageActions",
                "com.playwright.qa.automation.core.CommonPage"
        };

        String[] seleniumClasses = {
                "com.selenium.qa.automation.core.SeleniumPageActions",
                "com.selenium.qa.automation.core.CommonPage"
        };

        String[][] classesToTry;
        if (framework == Framework.PLAYWRIGHT) {
            classesToTry = new String[][]{playwrightClasses};
        } else if (framework == Framework.SELENIUM) {
            classesToTry = new String[][]{seleniumClasses};
        } else {
            classesToTry = new String[][]{playwrightClasses, seleniumClasses};
        }

        for (String[] classes : classesToTry) {
            for (String className : classes) {
                try {
                    Class<?> clazz = Class.forName(className);
                    if (PageActions.class.isAssignableFrom(clazz)) {
                        return (PageActions) clazz.getDeclaredConstructor().newInstance();
                    }
                } catch (ClassNotFoundException e) {
                    // Try next class
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to instantiate " + className, e);
                }
            }
        }

        throw new IllegalStateException(
                "No PageActions implementation found. " +
                "Ensure playwright-core or selenium-core is on the classpath."
        );
    }
}
