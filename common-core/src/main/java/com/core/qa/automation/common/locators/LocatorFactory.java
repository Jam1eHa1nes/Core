package com.core.qa.automation.common.locators;

/**
 * Factory for creating Locator instances.
 * Use this class to create locators in a framework-agnostic way.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     Locator loginButton = LocatorFactory.id("login-btn");
 *     Locator searchInput = LocatorFactory.css("input[type='search']");
 *     Locator heading = LocatorFactory.text("Welcome");
 * </pre>
 */
public class LocatorFactory {

    /**
     * Creates a locator by ID.
     *
     * @param id the element ID
     * @return a new Locator instance
     */
    public static Locator id(String id) {
        return new SimpleLocator(Locator.Type.ID, id);
    }

    /**
     * Creates a locator by CSS selector.
     *
     * @param css the CSS selector
     * @return a new Locator instance
     */
    public static Locator css(String css) {
        return new SimpleLocator(Locator.Type.CSS, css);
    }

    /**
     * Creates a locator by XPath.
     *
     * @param xpath the XPath expression
     * @return a new Locator instance
     */
    public static Locator xpath(String xpath) {
        return new SimpleLocator(Locator.Type.XPATH, xpath);
    }

    /**
     * Creates a locator by element name attribute.
     *
     * @param name the name attribute value
     * @return a new Locator instance
     */
    public static Locator name(String name) {
        return new SimpleLocator(Locator.Type.NAME, name);
    }

    /**
     * Creates a locator by class name.
     *
     * @param className the class name
     * @return a new Locator instance
     */
    public static Locator className(String className) {
        return new SimpleLocator(Locator.Type.CLASS, className);
    }

    /**
     * Creates a locator by tag name.
     *
     * @param tagName the tag name
     * @return a new Locator instance
     */
    public static Locator tag(String tagName) {
        return new SimpleLocator(Locator.Type.TAG, tagName);
    }

    /**
     * Creates a locator by exact text content.
     *
     * @param text the exact text to match
     * @return a new Locator instance
     */
    public static Locator text(String text) {
        return new SimpleLocator(Locator.Type.TEXT, text);
    }

    /**
     * Creates a locator by partial text content.
     *
     * @param partialText the partial text to match
     * @return a new Locator instance
     */
    public static Locator partialText(String partialText) {
        return new SimpleLocator(Locator.Type.PARTIAL_TEXT, partialText);
    }

    /**
     * Creates a locator by link text.
     *
     * @param linkText the link text
     * @return a new Locator instance
     */
    public static Locator linkText(String linkText) {
        return new SimpleLocator(Locator.Type.LINK_TEXT, linkText);
    }

    /**
     * Creates a locator by partial link text.
     *
     * @param partialLinkText the partial link text
     * @return a new Locator instance
     */
    public static Locator partialLinkText(String partialLinkText) {
        return new SimpleLocator(Locator.Type.PARTIAL_LINK_TEXT, partialLinkText);
    }

    /**
     * Creates a locator by placeholder attribute.
     *
     * @param placeholder the placeholder text
     * @return a new Locator instance
     */
    public static Locator placeholder(String placeholder) {
        return new SimpleLocator(Locator.Type.PLACEHOLDER, placeholder);
    }

    /**
     * Creates a locator by title attribute.
     *
     * @param title the title attribute value
     * @return a new Locator instance
     */
    public static Locator title(String title) {
        return new SimpleLocator(Locator.Type.TITLE, title);
    }

    /**
     * Creates a locator by ARIA role.
     *
     * @param role the ARIA role
     * @return a new Locator instance
     */
    public static Locator role(String role) {
        return new SimpleLocator(Locator.Type.ROLE, role);
    }

    /**
     * Creates a locator by data-testid attribute.
     *
     * @param testId the data-testid value
     * @return a new Locator instance
     */
    public static Locator testId(String testId) {
        return new SimpleLocator(Locator.Type.DATA_TEST_ID, testId);
    }

    /**
     * Creates a locator by custom data attribute.
     *
     * @param attributeName  the data attribute name (without 'data-' prefix)
     * @param attributeValue the attribute value
     * @return a new Locator instance
     */
    public static Locator dataAttribute(String attributeName, String attributeValue) {
        return new SimpleLocator(Locator.Type.DATA_ATTRIBUTE, attributeValue, attributeName);
    }

    /**
     * Creates a locator by tag name with specific text.
     *
     * @param tagName the tag name
     * @param text    the text content
     * @return a new Locator instance
     */
    public static Locator tagWithText(String tagName, String text) {
        return new SimpleLocator(Locator.Type.TAG_WITH_TEXT, text, tagName);
    }

    /**
     * Creates a locator by tag name containing text.
     *
     * @param tagName     the tag name
     * @param partialText the partial text content
     * @return a new Locator instance
     */
    public static Locator tagContainsText(String tagName, String partialText) {
        return new SimpleLocator(Locator.Type.TAG_CONTAINS_TEXT, partialText, tagName);
    }

    /**
     * Creates a locator by label text.
     *
     * @param labelText the label text
     * @return a new Locator instance
     */
    public static Locator label(String labelText) {
        return new SimpleLocator(Locator.Type.LABEL, labelText);
    }

    /**
     * Creates a locator by value attribute.
     *
     * @param value the value attribute
     * @return a new Locator instance
     */
    public static Locator value(String value) {
        return new SimpleLocator(Locator.Type.VALUE, value);
    }

    /**
     * Creates a locator by any attribute.
     *
     * @param attributeName  the attribute name
     * @param attributeValue the attribute value
     * @return a new Locator instance
     */
    public static Locator attribute(String attributeName, String attributeValue) {
        return new SimpleLocator(Locator.Type.ATTRIBUTE, attributeValue, attributeName);
    }

    /**
     * Simple implementation of Locator interface.
     */
    private static class SimpleLocator implements Locator {
        private final Type type;
        private final String value;
        private final String secondaryValue;

        SimpleLocator(Type type, String value) {
            this(type, value, null);
        }

        SimpleLocator(Type type, String value, String secondaryValue) {
            this.type = type;
            this.value = value;
            this.secondaryValue = secondaryValue;
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String getSecondaryValue() {
            return secondaryValue;
        }

        @Override
        public String toString() {
            if (secondaryValue != null) {
                return type.name() + "(" + secondaryValue + ", " + value + ")";
            }
            return type.name() + "(" + value + ")";
        }
    }
}
