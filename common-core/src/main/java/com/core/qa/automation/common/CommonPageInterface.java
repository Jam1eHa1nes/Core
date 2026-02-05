package com.core.qa.automation.common;

import java.util.List;

/**
 * Common interface defining page actions and checks for UI automation.
 * This interface is implemented by both Selenium and Playwright automation modules.
 * It provides chainable commands, element state probes, collection operations, DOM navigation, and more.
 * <p>
 * Methods that require driver/browser specific implementations are defined here
 * but implemented in the respective modules (selenium-core and playwright-core).
 */
public interface CommonPageInterface extends StorageProvider {

    ////////////////////////
    // Browser Directives //
    ////////////////////////

    /**
     * Sets the browser window to full screen mode.
     */
    void fullScreen();

    /**
     * Maximises the browser window.
     */
    void maximise();

    /**
     * Opens the default browser.
     */
    void open();

    /**
     * Navigates to the specified URL.
     *
     * @param url the URL to navigate to
     */
    void go(String url);

    /**
     * Refreshes the current page.
     */
    void refresh();

    /**
     * Closes the current browser window/tab.
     */
    void close();

    /**
     * Quits the browser session.
     */
    void quit();

    //////////////////
    // Element Info //
    //////////////////

    /**
     * Gets the current page URL.
     *
     * @return the URL as a string
     */
    String getUrl();

    /**
     * Gets the current page title.
     *
     * @return the title as a string
     */
    String getTitle();

    /////////////////
    // COLLECTIONS //
    /////////////////

    /**
     * Returns the size of the current collection.
     *
     * @return the number of elements in the collection
     */
    int size();

    ///////////////////////////////
    // COLLECTION CONTENT CHECKS //
    ///////////////////////////////

    /**
     * Asserts that the specified text is present in the collection.
     *
     * @param text the text to check for
     */
    void present(String text);

    /**
     * Asserts that all specified texts are present in the collection.
     *
     * @param textList the list of texts to check for
     */
    void present(List<String> textList);

    /**
     * Asserts that the specified text is absent from the collection.
     *
     * @param text the text to check for absence
     */
    void absent(String text);

    /**
     * Asserts that all specified texts are absent from the collection.
     *
     * @param textList the list of texts to check for absence
     */
    void absent(List<String> textList);

    /////////////////////////
    // Element Interaction //
    /////////////////////////

    /**
     * Clicks the currently focused or chosen element.
     */
    void click();

    /**
     * Double-clicks the currently focused or chosen element.
     */
    void dblClick();

    /**
     * Clears the value of the currently focused or chosen element.
     */
    void clear();

    /**
     * Hovers over the currently focused or chosen element.
     */
    void hover();

    ////////////////////////
    // Element Text Checks //
    ////////////////////////

    /**
     * Asserts that the element's text matches the specified value.
     *
     * @param text the text to match
     */
    void matches(String text);

    /**
     * Asserts that the element's text contains the specified partial text.
     *
     * @param partialText the partial text to check
     */
    void contains(String partialText);

    ////////////////////
    // Element States //
    ////////////////////

    /**
     * Asserts that the element is selected.
     */
    void selected();

    /**
     * Asserts that the element is unselected.
     */
    void unSelected();

    /**
     * Asserts that the element is enabled.
     */
    void enabled();

    /**
     * Asserts that the element is disabled.
     */
    void disabled();

    /**
     * Asserts that the element is clickable.
     */
    void clickable();

    /**
     * Asserts that the element is unclickable.
     */
    void unclickable();

    /**
     * Asserts that the element is visible.
     */
    void visible();

    /**
     * Asserts that the element is hidden.
     */
    void hidden();

    //////////////////////////
    // Reset Elements/Timers //
    //////////////////////////

    /**
     * Resets the state of elements and timers.
     */
    void reset();

    /**
     * Returns to the origin or starting point in the navigation.
     */
    void origin();

    /**
     * Ends the current probe or wait operation.
     */
    void end();

    /////////////////////
    // Session Storage //
    /////////////////////

    /**
     * Stores a value in session storage with the specified key.
     *
     * @param key   the key to store
     * @param value the value to store
     */
    @Override
    void store(String key, String value);

    /**
     * Stores an object in session storage with the specified key.
     *
     * @param key   the key to store
     * @param value the object to store
     */
    void store(String key, Object value);

    /**
     * Retrieves a value from session storage by key.
     *
     * @param key the key to retrieve
     * @return the stored value
     */
    @Override
    String retrieve(String key);

    /**
     * Retrieves an object from session storage by key.
     *
     * @param key the key to retrieve
     * @param o   the object type or default value
     * @return the stored object
     */
    Object retrieve(String key, Object o);

    /////////////////////
    // Element Storage //
    /////////////////////

    /**
     * Stores the currently focused element with the specified key.
     *
     * @param key the key to store
     */
    void storeFocused(String key);

    /**
     * Retrieves the focused element by key.
     *
     * @param key the key to retrieve
     */
    void retrieveFocused(String key);

    ////////////////////
    // Console Output //
    ////////////////////

    /**
     * Prints the currently focused element to the console.
     */
    void printFocused();

    /**
     * Prints the current collection to the console.
     */
    void printCollection();

    /**
     * Logs the specified arguments to the console or log file.
     *
     * @param args the arguments to log
     */
    void log(String... args);

    //////////////////////
    // Event Generators //
    //////////////////////

    /**
     * Executes the given JavaScript in the browser context.
     *
     * @param script the JavaScript code to execute
     * @return the result of the script execution
     */
    String javascript(String script);

    /**
     * Pauses execution for the specified time.
     *
     * @param milliSeconds time to pause in milliseconds
     */
    void pause(long milliSeconds);

    /**
     * Pauses execution for the specified time.
     *
     * @param seconds time to pause in seconds
     */
    void pause(int seconds);

    /////////////////
    // Screenshots //
    /////////////////

    /**
     * Takes a screenshot and returns it as a byte array.
     * Useful for attaching to test reports.
     *
     * @return screenshot as byte array, or null if screenshot failed
     */
    byte[] getScreenshotBytes();

    /**
     * Takes a screenshot and exits the test.
     *
     * @param message the message to display
     */
    void takeScreenShotAndExit(String message);
}
