package com.core.qa.automation.common;

import com.core.qa.automation.common.locators.Locator;

import java.util.List;

/**
 * Framework-agnostic interface for page automation actions.
 * Both Playwright and Selenium implementations will implement this interface,
 * allowing tests to be written without coupling to a specific framework.
 * <p>
 * <b>Example usage in tests:</b>
 * <pre>
 *     PageActions page = PageFactory.create();
 *     page.open(Browser.CHROME)
 *         .go("https://example.com")
 *         .focus(LocatorFactory.id("username"))
 *         .type("testuser")
 *         .focus(LocatorFactory.id("password"))
 *         .type("secret")
 *         .focus(LocatorFactory.id("login-btn"))
 *         .click();
 * </pre>
 */
public interface PageActions extends StorageProvider {

    // ========================
    // Browser Management
    // ========================

    /**
     * Opens the default browser.
     *
     * @return this instance for method chaining
     */
    PageActions open();

    /**
     * Opens the specified browser.
     *
     * @param browser the browser to open
     * @return this instance for method chaining
     */
    PageActions open(FrameworkEnums.Browser browser);

    /**
     * Navigates to the specified URL.
     *
     * @param url the URL to navigate to
     * @return this instance for method chaining
     */
    PageActions go(String url);

    /**
     * Navigates in the specified direction.
     *
     * @param direction FORWARD or BACK
     * @return this instance for method chaining
     */
    PageActions go(FrameworkEnums.Direction direction);

    /**
     * Refreshes the current page.
     *
     * @return this instance for method chaining
     */
    PageActions refresh();

    /**
     * Closes the current window/tab.
     *
     * @return this instance for method chaining
     */
    PageActions close();

    /**
     * Quits the browser completely.
     */
    void quit();

    /**
     * Sets the browser window to full screen.
     *
     * @return this instance for method chaining
     */
    PageActions fullScreen();

    /**
     * Maximizes the browser window.
     *
     * @return this instance for method chaining
     */
    PageActions maximise();

    // ========================
    // Page Information
    // ========================

    /**
     * Gets the current page URL.
     *
     * @return the current URL
     */
    String getUrl();

    /**
     * Gets the current page title.
     *
     * @return the page title
     */
    String getTitle();

    // ========================
    // Element Targeting
    // ========================

    /**
     * Focuses on an element identified by the locator.
     *
     * @param locator the element locator
     * @return this instance for method chaining
     */
    PageActions focus(Locator locator);

    /**
     * Focuses on an element with a wait time.
     *
     * @param locator    the element locator
     * @param waitTimeMs wait time in milliseconds
     * @return this instance for method chaining
     */
    PageActions focus(Locator locator, int waitTimeMs);

    /**
     * Checks if an element is visible without throwing an exception.
     *
     * @param locator the element locator
     * @return true if the element is visible
     */
    boolean peek(Locator locator);

    /**
     * Checks if an element is visible with a timeout.
     *
     * @param locator    the element locator
     * @param waitTimeMs timeout in milliseconds
     * @return true if the element is visible within the timeout
     */
    boolean peek(Locator locator, int waitTimeMs);

    // ========================
    // Element Interaction
    // ========================

    /**
     * Clicks the currently focused element.
     *
     * @return this instance for method chaining
     */
    PageActions click();

    /**
     * Double-clicks the currently focused element.
     *
     * @return this instance for method chaining
     */
    PageActions dblClick();

    /**
     * Types text into the currently focused element.
     *
     * @param text the text to type
     * @return this instance for method chaining
     */
    PageActions type(String text);

    /**
     * Clears the currently focused element and types new text.
     *
     * @param text the text to fill
     * @return this instance for method chaining
     */
    PageActions fill(String text);

    /**
     * Clears the currently focused element.
     *
     * @return this instance for method chaining
     */
    PageActions clear();

    /**
     * Hovers over the currently focused element.
     *
     * @return this instance for method chaining
     */
    PageActions hover();

    /**
     * Hovers over the specified element.
     *
     * @param locator the element to hover over
     * @return this instance for method chaining
     */
    PageActions hover(Locator locator);

    /**
     * Drags an element and drops it on another.
     *
     * @param source the element to drag
     * @param target the drop target
     * @return this instance for method chaining
     */
    PageActions dragAndDrop(Locator source, Locator target);

    /**
     * Uploads a file to a file input element.
     *
     * @param filePath the path to the file
     * @return this instance for method chaining
     */
    PageActions uploadFile(String filePath);

    // ========================
    // Element Information
    // ========================

    /**
     * Gets the text content of the currently focused element.
     *
     * @return the element's text content
     */
    String getText();

    /**
     * Gets an attribute value of the currently focused element.
     *
     * @param trait the attribute/trait to get
     * @return the attribute value
     */
    String get(FrameworkEnums.ElementTrait trait);

    /**
     * Gets a custom attribute value.
     *
     * @param attributeName the attribute name
     * @return the attribute value
     */
    String getAttribute(String attributeName);

    // ========================
    // Collections
    // ========================

    /**
     * Collects multiple elements matching the locator.
     *
     * @param locator the locator for elements to collect
     * @return this instance for method chaining
     */
    PageActions collect(Locator locator);

    /**
     * Returns the size of the current collection.
     *
     * @return the number of elements
     */
    int size();

    /**
     * Selects an element from the collection by index.
     *
     * @param index the index (0-based)
     * @return this instance for method chaining
     */
    PageActions choose(int index);

    /**
     * Selects an element from the collection by text.
     *
     * @param text the text to match
     * @return this instance for method chaining
     */
    PageActions choose(String text);

    /**
     * Selects an element from the collection by position.
     *
     * @param index FIRST, LAST, or RANDOM
     * @return this instance for method chaining
     */
    PageActions choose(FrameworkEnums.Index index);

    /**
     * Gets the text content of all elements in the collection.
     *
     * @return list of text contents
     */
    List<String> getCollectionTexts();

    // ========================
    // Assertions
    // ========================

    /**
     * Asserts the element text matches exactly.
     *
     * @param expectedText the expected text
     * @return this instance for method chaining
     */
    PageActions matches(String expectedText);

    /**
     * Asserts the element text contains the specified text.
     *
     * @param partialText the text to look for
     * @return this instance for method chaining
     */
    PageActions contains(String partialText);

    /**
     * Asserts the element is in the specified state.
     *
     * @param state the expected state
     * @return this instance for method chaining
     */
    PageActions assertState(FrameworkEnums.ElementState state);

    /**
     * Asserts the specified text is present in the collection.
     *
     * @param text the text to find
     * @return this instance for method chaining
     */
    PageActions present(String text);

    /**
     * Asserts the specified text is absent from the collection.
     *
     * @param text the text that should not be present
     * @return this instance for method chaining
     */
    PageActions absent(String text);

    // ========================
    // Waits
    // ========================

    /**
     * Waits for an element to be in the specified state.
     *
     * @param locator the element locator
     * @param state   the expected state
     * @return this instance for method chaining
     */
    PageActions waitFor(Locator locator, FrameworkEnums.ElementState state);

    /**
     * Waits for an element to be in the specified state with timeout.
     *
     * @param locator    the element locator
     * @param state      the expected state
     * @param waitTimeMs timeout in milliseconds
     * @return this instance for method chaining
     */
    PageActions waitFor(Locator locator, FrameworkEnums.ElementState state, int waitTimeMs);

    /**
     * Pauses execution for the specified time.
     *
     * @param milliseconds time to pause
     * @return this instance for method chaining
     */
    PageActions pause(long milliseconds);

    // ========================
    // Frames
    // ========================

    /**
     * Switches to a frame by name or ID.
     *
     * @param nameOrId the frame name or ID
     * @return this instance for method chaining
     */
    PageActions frame(String nameOrId);

    /**
     * Switches to a frame by index.
     *
     * @param index the frame index
     * @return this instance for method chaining
     */
    PageActions frame(int index);

    /**
     * Switches to a frame by locator.
     *
     * @param locator the frame locator
     * @return this instance for method chaining
     */
    PageActions frame(Locator locator);

    /**
     * Switches back to the main content.
     *
     * @return this instance for method chaining
     */
    PageActions defaultContent();

    // ========================
    // Windows/Tabs
    // ========================

    /**
     * Switches to a window.
     *
     * @param window the window selector
     * @return this instance for method chaining
     */
    PageActions window(FrameworkEnums.Window window);

    /**
     * Switches to a window by index.
     *
     * @param index the window index
     * @return this instance for method chaining
     */
    PageActions window(int index);

    // ========================
    // JavaScript
    // ========================

    /**
     * Executes JavaScript on the page.
     *
     * @param script the JavaScript to execute
     * @return the result of the script
     */
    Object executeScript(String script);

    /**
     * Executes JavaScript on the page with arguments.
     *
     * @param script the JavaScript to execute
     * @param args   arguments to pass to the script
     * @return the result of the script
     */
    Object executeScript(String script, Object... args);

    // ========================
    // Screenshots
    // ========================

    /**
     * Takes a screenshot.
     *
     * @param filename the filename to save as
     * @return this instance for method chaining
     */
    PageActions screenshot(String filename);

    /**
     * Takes a screenshot and fails the test.
     *
     * @param message the failure message
     */
    void screenshotAndFail(String message);

    // ========================
    // Alerts
    // ========================

    /**
     * Handles an alert dialog.
     *
     * @param action ACCEPT or DISMISS
     * @return this instance for method chaining
     */
    PageActions alert(FrameworkEnums.AlertAction action);

    /**
     * Gets the text from an alert dialog.
     *
     * @return the alert text
     */
    String getAlertText();

    /**
     * Types into an alert prompt and handles it.
     *
     * @param text   the text to type
     * @param action ACCEPT or DISMISS
     * @return this instance for method chaining
     */
    PageActions alertPrompt(String text, FrameworkEnums.AlertAction action);

    // ========================
    // Logging
    // ========================

    /**
     * Logs a message.
     *
     * @param messages the messages to log
     * @return this instance for method chaining
     */
    PageActions log(String... messages);
}
