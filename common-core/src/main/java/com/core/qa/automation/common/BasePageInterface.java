package com.core.qa.automation.common;

import java.util.List;

/**
 * Common interface defining page actions and checks for UI automation.
 * This interface is implemented by both Selenium and Playwright automation modules.
 * It provides chainable commands, element state probes, collection operations, DOM navigation, and more.
 * <p>
 * Uses generics to allow framework-specific types while maintaining a common contract.
 *
 * @param <T> the target/locator type (e.g., Target for Playwright, By for Selenium)
 * @param <B> the browser enum type
 * @param <D> the direction enum type  
 * @param <S> the element state enum type
 * @param <E> the element trait enum type
 */
public interface BasePageInterface<T, B, D, S, E> extends StorageProvider {

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
     * Opens the specified browser.
     *
     * @param browser the browser to open
     */
    void open(B browser);

    /**
     * Navigates to the specified URL.
     *
     * @param url the URL to navigate to
     */
    void go(String url);

    /**
     * Navigates in the specified direction (e.g., FORWARD, BACK).
     *
     * @param direction the direction to navigate
     */
    void go(D direction);

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

    ////////////////////////
    // Focus and Targeting //
    ////////////////////////

    /**
     * Focuses on the specified target element.
     *
     * @param target the element to focus
     */
    void focus(T target);

    /**
     * Waits for the specified time and focuses on the current element.
     *
     * @param waitTime time to wait in milliseconds
     */
    void focus(int waitTime);

    /**
     * Peeks at the target element (checks if visible without throwing exception).
     *
     * @param target the element to peek at
     * @return true if element is visible
     */
    boolean peek(T target);

    /**
     * Peeks at the target element with specified wait time.
     *
     * @param target   the element to peek at
     * @param waitTime time to wait in milliseconds
     * @return true if element is visible
     */
    boolean peek(T target, int waitTime);

    /**
     * Departs from the specified target element (e.g., blur or unfocus).
     *
     * @param target the element to depart from
     */
    void depart(T target);

    /**
     * Marks the specified target as absent.
     *
     * @param target the element to mark as absent
     */
    void absent(T target);

    /**
     * Returns to the origin or starting point in the navigation.
     */
    void origin();

    ////////////////////////
    // PROBE CONDITIONALS //
    ////////////////////////

    /**
     * Probes the specified target for visibility (default state).
     *
     * @param target the element to probe
     */
    void probe(T target);

    /**
     * Probes the specified target for a given element state.
     *
     * @param target       the element to probe
     * @param elementState the state to check (e.g., VISIBLE, ENABLED)
     */
    void probe(T target, S elementState);

    /**
     * Waits for the specified time and probes the current element.
     *
     * @param waitTime time to wait in milliseconds
     */
    void probe(int waitTime);

    /**
     * Ends the current probe or wait operation.
     */
    void end();

    /////////////////
    // COLLECTIONS //
    /////////////////

    /**
     * Collects the specified target element into a collection.
     *
     * @param target the element to collect
     */
    void collect(T target);

    /**
     * Collects a list of target elements into a collection.
     *
     * @param targets the list of elements to collect
     */
    void collect(List<T> targets);

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

    /////////////
    // CHOOSE //
    /////////////

    /**
     * Chooses an element by its index in a collection.
     *
     * @param index the index to choose
     */
    void choose(int index);

    /**
     * Chooses an element by its visible text.
     *
     * @param text the text to match
     */
    void choose(String text);

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

    /**
     * Hovers over the specified target element.
     *
     * @param target the element to hover over
     */
    void hover(T target);

    /**
     * Drags the draggable element and drops it onto the drop zone.
     *
     * @param draggable the element to drag
     * @param dropZone  the element to drop onto
     */
    void dragDrop(T draggable, T dropZone);

    /////////////////////////////////////
    // Interactions with return values //
    /////////////////////////////////////

    /**
     * Gets the specified trait of the current element.
     *
     * @param elementTrait the trait to retrieve
     * @return the value of the trait
     */
    String get(E elementTrait);

    /**
     * Uploads a file to the currently focused or chosen element.
     *
     * @param filePath the path to the file
     */
    void file(String filePath);

    ////////////
    // Frames //
    ////////////

    /**
     * Switches to the frame with the specified ID or name.
     *
     * @param idOrName the frame ID or name
     */
    void frame(String idOrName);

    /**
     * Switches to the frame specified by the target element.
     *
     * @param target the frame element
     */
    void frame(T target);

    /**
     * Switches to the frame at the specified index.
     *
     * @param index the frame index
     */
    void frame(int index);

    /**
     * Switches to the default content (main frame).
     */
    void frame();

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
     * Takes a screenshot of the current page.
     *
     * @param filename the filename to save the screenshot
     */
    void screenshot(String filename);

    /**
     * Takes a screenshot and exits the test.
     *
     * @param message the message to display
     */
    void takeScreenShotAndExit(String message);
}
