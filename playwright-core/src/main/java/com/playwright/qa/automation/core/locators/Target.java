package com.playwright.qa.automation.core.locators;

/**
 * Target class representing a locator for Playwright elements.
 * Unlike Selenium's By class, Playwright uses CSS selectors and text-based selectors natively.
 */
public class Target {

    public enum Type {
        ID,
        TEXT,
        PARTIAL_TEXT,
        TAG_WITH_TEXT,
        TAG_CONTAINS_TEXT,
        TAG_WITH_ID,
        TAG_WITH_CLASS,
        TAG_WITH_TITLE,
        TAG_WITH_NAME,
        TAG_WITH_VALUE,
        CLASS,
        VALUE,
        CLASSES,
        CSS,
        TAG,
        NAME,
        PLACEHOLDER,
        LINK_TEXT,
        PARTIAL_LINK_TEXT,
        DATA_ATTRIBUTE,
        XPATH,
        ROOT,
        LEAF,
        CHILDREN,
        SIBLINGS,
        ATTRIBUTE,
        ROLE,
        TITLE,
        HREF,
        SRC,
        STYLE,
        ALT,
        TYPE,
        DATA_ENDPOINT,
        DATA_FIELD,
        DATA_TEST_ID,
        DATA_ICON_NAME,
        SPLICE,
        // Playwright-specific types
        TEST_ID,
        LABEL,
        ALT_TEXT,
        GET_BY_ROLE;
    }

    public enum AlertAction {
        ACCEPT,
        DISMISS;
    }

    private String selector;
    private String key;
    private String value;
    private Type type;

    /**
     * Constructor for targets with a selector string.
     *
     * @param selector The Playwright selector string
     * @param value    The original value used to create the selector
     * @param type     The type of locator
     */
    public Target(String selector, String value, Type type) {
        this.selector = selector;
        this.value = value;
        this.type = type;
    }

    /**
     * Constructor for targets with a selector string and key/value pair.
     *
     * @param selector The Playwright selector string
     * @param key      The attribute key
     * @param value    The attribute value
     * @param type     The type of locator
     */
    public Target(String selector, String key, String value, Type type) {
        this.selector = selector;
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public String getSelector() {
        return selector;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    /**
     * Returns a string representation of the selector for logging.
     */
    @Override
    public String toString() {
        return String.format("Target[type=%s, selector=%s]", type, selector);
    }
}

