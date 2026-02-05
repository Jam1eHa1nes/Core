package com.core.qa.automation.tests.po;

import com.core.qa.automation.common.PageActions;
import com.core.qa.automation.common.PageFactory;
import com.core.qa.automation.common.logger.Logger;

/**
 * Page Object for Playwright documentation page.
 * Uses PlaywrightPageLocators for element targeting.
 */
public class PlaywrightPagePO {

    private final Logger logger = new Logger();
    private final PageActions page;

    public PlaywrightPagePO() {
        this.page = PageFactory.create();
    }

    /**
     * Opens the browser and navigates to the Playwright documentation page.
     */
    public void openPlaywrightDocsPage() {
        logger.log("Opening Playwright documentation page");
        page.open()
            .maximise()
            .go(PlaywrightPageLocators.PLAYWRIGHT_DOCS_URL);
    }

    /**
     * Clicks the search button to open the search dialog.
     */
    public void clickSearchButton() {
        logger.log("Clicking search button");
        page.focus(PlaywrightPageLocators.SEARCH_BUTTON)
            .click();
    }

    /**
     * Enters text into the search input field.
     *
     * @param searchText the text to enter
     */
    public void enterSearchText(String searchText) {
        logger.log("Entering search text: " + searchText);
        page.focus(PlaywrightPageLocators.SEARCH_INPUT)
            .type(searchText);
    }

    /**
     * Performs a search by clicking the search button and entering text.
     *
     * @param searchText the text to search for
     */
    public void search(String searchText) {
        clickSearchButton();
        page.pause(500); // Wait for search dialog to open
        enterSearchText(searchText);
    }

    /**
     * Checks if search results are displayed.
     *
     * @return true if search results are visible
     */
    public boolean areSearchResultsDisplayed() {
        logger.log("Checking if search results are displayed");
        return page.peek(PlaywrightPageLocators.SEARCH_RESULTS);
    }

    /**
     * Checks if search results contain a specific text.
     *
     * @param text the text to search for
     * @return true if results contain the text
     */
    public boolean searchResultsContainText(String text) {
        logger.log("Checking if search results contain: " + text);
        return page.peek(PlaywrightPageLocators.searchResultWithText(text));
    }

    /**
     * Clicks on a navigation link by its text.
     *
     * @param linkText the visible text of the link
     */
    public void clickNavLink(String linkText) {
        logger.log("Clicking navigation link: " + linkText);
        page.focus(PlaywrightPageLocators.navLink(linkText))
            .click();
    }

    /**
     * Closes the browser.
     */
    public void closeBrowser() {
        logger.log("Closing browser");
        page.quit();
    }
}
