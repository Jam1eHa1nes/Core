package com.playwright.qa.automation.core;

import com.playwright.qa.automation.core.locators.Target;

import java.util.List;

/**
 * Interface defining common page actions and checks for UI automation using Playwright.
 * Provides chainable commands, element state probes, collection operations, DOM navigation, and more.
 */
public interface CommonPageInterface {

    ////////////////////////
    // Playwright-Specific //
    ////////////////////////

    /**
     * Sets the browser window to full screen mode.
     */
    void fullScreen();

    /**
     * Maximises the browser window.
     */
    void maximise();

    // Browser directives

    /**
     * Opens the default browser and navigates to the base URL.
     */
    void open();

    /**
     * Opens the specified browser and navigates to the base URL.
     *
     * @param browser the browser to open
     */
    void open(Enums.Browser browser);

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
    void go(Enums.Direction direction);

    /**
     * Refreshes the current page.
     */
    void refresh();

    /**
     * Closes the current browser window/tab.
     */
    void close();

    /**
     * Closes the browser completely.
     */
    void quit();

    /**
     * Focuses on the specified target element.
     *
     * @param target the element to focus
     */
    void focus(Target target);

    /**
     * Waits for the specified time and focuses on the current element.
     *
     * @param waitTime time to wait in milliseconds
     */
    void focus(int waitTime);

    /**
     * Focuses on the specified target elements in order.
     *
     * @param targets the elements to focus
     */
    void focus(Target... targets);

    /**
     * Waits for the specified time and focuses on the given target elements.
     *
     * @param waitTime time to wait in milliseconds
     * @param targets  the elements to focus
     */
    void focus(int waitTime, Target... targets);

    /**
     * Peeks at the target element (checks if visible without throwing exception).
     *
     * @param target the element to peek at
     * @return true if element is visible
     */
    boolean peek(Target target);

    /**
     * Peeks at the target element with specified wait time.
     *
     * @param target   the element to peek at
     * @param waitTime time to wait in milliseconds
     * @return true if element is visible
     */
    boolean peek(Target target, int waitTime);

    /**
     * Sets the peek wait time.
     *
     * @param waitTime time to wait in milliseconds
     */
    void peek(int waitTime);

    /**
     * Departs from the specified target element (e.g., blur or unfocus).
     *
     * @param target the element to depart from
     */
    void depart(Target target);

    /**
     * Departs from the specified target element and targets group.
     *
     * @param target  the element to depart from
     * @param targets the group of targets
     */
    void depart(Target target, Enums.Targets targets);

    /**
     * Departs from a list of target elements.
     *
     * @param targets the list of elements to depart from
     */
    void depart(List<Target> targets);

    /**
     * Marks the specified target as absent (e.g., removes or hides it).
     *
     * @param target the element to mark as absent
     */
    void absent(Target target);

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
    void probe(Target target);

    /**
     * Probes the specified target for a given element state.
     *
     * @param target       the element to probe
     * @param elementState the state to check (e.g., VISIBLE, ENABLED)
     */
    void probe(Target target, Enums.ElementState elementState);

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

    /**
     * Collects the specified target element into a collection for further operations.
     *
     * @param target the element to collect
     */
    void collect(Target target);

    /**
     * Collects a list of target elements into a collection for further operations.
     *
     * @param targets the list of elements to collect
     */
    void collect(List<Target> targets);

    /**
     * Waits for the specified time and collects elements.
     *
     * @param waitTime time to wait in seconds
     */
    void collect(int waitTime);

    /**
     * Finds the required target element relative to the origin (source) element location in the DOM hierarchy.
     *
     * @param origin the source element to start the search from
     * @param target the target element to find relative to the source
     */
    void cognate(Target origin, Target target);

    /**
     * Finds the required target element relative to the currently focused element.
     *
     * @param target the target element to find relative to the currently focused element
     */
    void cognate(Target target);

    /**
     * Sets wait time for the cognate operation.
     *
     * @param waitTime time to wait in seconds
     */
    void cognate(int waitTime);

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

    /**
     * Chooses an element by its visible text, with optional leniency.
     *
     * @param text    the text to match
     * @param lenient whether to use lenient matching
     */
    void choose(String text, boolean lenient);

    /**
     * Chooses the specified target element.
     *
     * @param target the element to choose
     */
    void choose(Target target);

    /**
     * Chooses an element by its index (Enums.Index) in a collection.
     *
     * @param index the index to choose
     */
    void choose(Enums.Index index);

    /**
     * Chooses an element by its list index (Enums.ListIndex).
     *
     * @param listIndex the list index to choose
     */
    void choose(Enums.ListIndex listIndex);

    /**
     * Expands the specified target element (e.g., dropdown, accordion).
     *
     * @param target the element to expand
     */
    void expand(Target target);

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

    // Element interaction

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
    void hover(Target target);

    /**
     * Drags the draggable element and drops it onto the drop zone.
     *
     * @param draggable the element to drag
     * @param dropZone  the element to drop onto
     */
    void dragDrop(Target draggable, Target dropZone);

    /**
     * Drops the currently focused or chosen element onto the drop zone.
     *
     * @param dropZone the element to drop onto
     */
    void drop(Target dropZone);

    /**
     * Drags the draggable element onto the currently focused or chosen element.
     *
     * @param draggable the element to drag
     */
    void drag(Target draggable);

    // Interactions with return values

    /**
     * Gets the specified trait of the current element.
     *
     * @param elementTrait the trait to retrieve
     * @return the value of the trait
     */
    String get(Enums.ElementTrait elementTrait);

    /**
     * Waits for the specified time and gets the value of the current element.
     *
     * @param getWaitTime time to wait in milliseconds
     */
    void get(int getWaitTime);

    /**
     * Uploads a file to the currently focused or chosen element.
     *
     * @param filePath the path to the file
     */
    void file(String filePath);

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

    // Frames

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
    void frame(Target target);

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

    /**
     * Exits the current frame and returns to the parent frame.
     */
    void deframe();

    // Windows/Tabs (Playwright uses contexts and pages)

    /**
     * Switches to the specified window/page.
     *
     * @param window the window to switch to
     */
    void window(Enums.Window window);

    /**
     * Switches to the window/page at the specified index.
     *
     * @param index the window index
     */
    void window(int index);

    // Element text checks

    /**
     * Asserts that the element's text matches the specified value.
     *
     * @param text the text to match
     */
    void matches(String text);

    /**
     * Asserts that the element's text matches the specified value, with content display option.
     *
     * @param text      the text to match
     * @param displayed the content display option
     */
    void matches(String text, Enums.Content displayed);

    /**
     * Asserts that the element's text contains the specified partial text.
     *
     * @param partialText the partial text to check
     */
    void contains(String partialText);

    /**
     * Asserts that the element's text contains the specified partial text, with content display option.
     *
     * @param partialText the partial text to check
     * @param displayed   the content display option
     */
    void contains(String partialText, Enums.Content displayed);

    // Element states

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

    /**
     * Asserts that the element is editable.
     */
    void editable();

    // Reset Elements and timers

    /**
     * Resets the state of elements and timers.
     */
    void reset();

    // Session storage

    /**
     * Stores a value in session storage with the specified key.
     *
     * @param key   the key to store
     * @param value the value to store
     */
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
    String retrieve(String key);

    /**
     * Retrieves an object from session storage by key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     Object user = page.retrieve("user", new User());
     * </pre>
     *
     * @param key the key to retrieve
     * @param o   the object type or default value
     * @return the stored object
     */
    Object retrieve(String key, Object o);

    // Element storage

    /**
     * Stores the currently focused element with the specified key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.storeFocused("mainField");
     * </pre>
     *
     * @param key the key to store
     */
    void storeFocused(String key);

    /**
     * Retrieves the focused element by key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.retrieveFocused("mainField");
     * </pre>
     *
     * @param key the key to retrieve
     */
    void retrieveFocused(String key);

    // Console Output

    /**
     * Prints the currently focused element to the console.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.printFocused();
     * </pre>
     */
    void printFocused();

    /**
     * Prints the current collection to the console.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.printCollection();
     * </pre>
     */
    void printCollection();

    /**
     * Logs the specified arguments to the console or log file.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.log("Step started", "User logged in");
     * </pre>
     *
     * @param args the arguments to log
     */
    void log(String... args);

    /**
     * Enables or disables network logging of the specified type.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.networkLogging("request");
     * </pre>
     *
     * @param type the type of network logging (e.g., 'request', 'response')
     */
    void networkLogging(String type);

    /**
     * Creates a loop builder for repeated actions on the given page object.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     LoopBuilder builder = page.loop(myPageObject);
     * </pre>
     *
     * @param commonPageObject the page object to loop on
     * @return a LoopBuilder instance
     */
    LoopBuilder loop(CommonPageObject commonPageObject);

    /**
     * Creates a loop builder for repeated actions on the given page object, for a specified number of times.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     LoopBuilder builder = page.loop(myPageObject, 5);
     * </pre>
     *
     * @param commonPageObject the page object to loop on
     * @param times            the number of times to repeat
     * @return a LoopBuilder instance
     */
    LoopBuilder loop(CommonPageObject commonPageObject, int times);

    /**
     * Performs a complex action defined by the given Performable object.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.perform(new CustomAction());
     * </pre>
     *
     * @param val the performable action
     * @param <T> the type of performable
     */
    <T extends com.playwright.qa.automation.core.performable.Performable> void perform(T val);

    // EVENT GENERATORS

    /**
     * Triggers the specified event on the current element.
     *
     * @param event the event to trigger
     */
    void trigger(Enums.Event event);

    /**
     * Executes the given JavaScript in the browser context.
     *
     * @param script the JavaScript code to execute
     * @return the result of the script execution
     */
    String javascript(String script);

    // Screenshot

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

    // Playwright specific methods

    /**
     * Waits for a network response matching the URL pattern.
     *
     * @param urlPattern the URL pattern to match
     */
    void waitForResponse(String urlPattern);

    /**
     * Waits for a request matching the URL pattern.
     *
     * @param urlPattern the URL pattern to match
     */
    void waitForRequest(String urlPattern);

    /**
     * Waits for navigation to complete.
     */
    void waitForNavigation();

    /**
     * Waits for the page to load completely.
     */
    void waitForLoadState();

    /**
     * Evaluates JavaScript on the page.
     *
     * @param script the JavaScript to execute
     * @return the result of the script
     */
    Object evaluate(String script);

    /**
     * Sets up route interception for the given URL pattern.
     *
     * @param urlPattern the URL pattern to intercept
     * @param handler    the handler for the route
     */
    void route(String urlPattern, RouteHandler handler);

    /**
     * Pauses execution for the specified number of milliseconds.
     *
     * @param milliSeconds time to pause in milliseconds
     */
    void pause(long milliSeconds);

    /**
     * Pauses execution for the specified number of seconds.
     *
     * @param seconds time to pause in seconds
     */
    void pause(int seconds);

    /**
     * Interface for route handling.
     */
    @FunctionalInterface
    interface RouteHandler {
        void handle(Object route);
    }
}

