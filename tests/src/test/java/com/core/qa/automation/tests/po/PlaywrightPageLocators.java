package com.core.qa.automation.tests.po;

import com.core.qa.automation.common.locators.Locator;
import com.core.qa.automation.common.locators.LocatorFactory;

/**
 * Centralized locator storage for Playwright documentation page elements.
 * Locators are stored as constants and referenced by Page Objects.
 */
public class PlaywrightPageLocators {

    // URLs
    public static final String PLAYWRIGHT_DOCS_URL = "https://playwright.dev/";

    // Search elements
    public static final Locator SEARCH_BUTTON = LocatorFactory.className("DocSearch");
    public static final Locator SEARCH_INPUT = LocatorFactory.id("docsearch-input");
    public static final Locator SEARCH_RESULTS = LocatorFactory.className("DocSearch-Dropdown");
    public static final Locator SEARCH_HIT = LocatorFactory.className("DocSearch-Hit");

    // Navigation elements
    public static final Locator NAV_DOCS_LINK = LocatorFactory.attribute("href", "/docs/intro");
    public static final Locator NAV_API_LINK = LocatorFactory.attribute("href", "/docs/api/class-playwright");

    /**
     * Returns a locator for a navigation link by text.
     *
     * @param linkText the visible text of the link
     * @return the Locator
     */
    public static Locator navLink(String linkText) {
        return LocatorFactory.partialLinkText(linkText);
    }

    /**
     * Returns a locator for a search result containing specific text.
     *
     * @param text the text to search for in results
     * @return the Locator
     */
    public static Locator searchResultWithText(String text) {
        return LocatorFactory.tagContainsText("a", text);
    }
}
