package com.core.qa.automation.common.locators;

/**
 * Common interface for element locators.
 * Both Playwright and Selenium modules implement this interface.
 * <p>
 * This abstraction allows tests to be written without coupling to a specific framework.
 */
public interface Locator {

    /**
     * Common locator types supported by both frameworks.
     */
    enum Type {
        ID,
        CSS,
        XPATH,
        NAME,
        CLASS,
        TAG,
        TEXT,
        PARTIAL_TEXT,
        LINK_TEXT,
        PARTIAL_LINK_TEXT,
        PLACEHOLDER,
        TITLE,
        ROLE,
        DATA_TEST_ID,
        DATA_ATTRIBUTE,
        TAG_WITH_TEXT,
        TAG_CONTAINS_TEXT,
        TAG_WITH_ID,
        TAG_WITH_CLASS,
        TAG_WITH_NAME,
        TAG_WITH_VALUE,
        TAG_WITH_TITLE,
        LABEL,
        VALUE,
        ATTRIBUTE
    }

    /**
     * Gets the locator type.
     *
     * @return the locator type
     */
    Type getType();

    /**
     * Gets the locator value.
     *
     * @return the locator value
     */
    String getValue();

    /**
     * Gets the secondary value (e.g., tag name for TAG_WITH_TEXT).
     *
     * @return the secondary value, or null if not applicable
     */
    String getSecondaryValue();

    /**
     * Gets the string representation of this locator for logging.
     *
     * @return string representation
     */
    @Override
    String toString();
}
