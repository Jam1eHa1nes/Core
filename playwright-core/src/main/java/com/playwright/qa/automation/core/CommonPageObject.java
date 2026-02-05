package com.playwright.qa.automation.core;

import com.playwright.qa.automation.core.locators.Target;
import com.playwright.qa.automation.core.locators.TargetFactory;
import com.playwright.qa.automation.core.performable.Performable;

import java.util.List;

/**
 * Base class for Page Objects using Playwright.
 * Provides a fluent API for browser automation with method chaining.
 * <p>
 * Extend this class to create page-specific implementations.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     public class LoginPage extends CommonPageObject {
 *         public LoginPage login(String username, String password) {
 *             focus(USERNAME_FIELD).clear().type(username);
 *             focus(PASSWORD_FIELD).clear().type(password);
 *             focus(LOGIN_BUTTON).click();
 *             return this;
 *         }
 *     }
 * </pre>
 */
public class CommonPageObject extends TargetFactory {

    // Singletons
    private final CommonPage commonPage = CommonPage.getInstance();
    private final Keyboard keyboard = Keyboard.getInstance();
    private final Navigation navigation = Navigation.getInstance();

    /**
     * Creates a LoopBuilder for iterating over the current collection.
     *
     * @return a new LoopBuilder instance
     */
    public LoopBuilder loop() {
        return commonPage.loop(this);
    }

    /**
     * Creates a LoopBuilder for repeating actions a specified number of times.
     *
     * @param times the number of times to repeat
     * @return a new LoopBuilder instance
     */
    public LoopBuilder loop(int times) {
        return commonPage.loop(this, times);
    }

    // ========================
    // Browser Control
    // ========================

    /**
     * Opens the default browser (Chromium).
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject open() {
        commonPage.open();
        return this;
    }

    /**
     * Opens the specified browser.
     *
     * @param browser the browser to open
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject open(Enums.Browser browser) {
        commonPage.open(browser);
        return this;
    }

    /**
     * Navigates to the specified URL.
     *
     * @param url the URL to navigate to
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject go(String url) {
        commonPage.go(url);
        origin();
        return this;
    }

    /**
     * Navigates in the specified direction (BACK, FORWARD, REFRESH).
     *
     * @param direction the navigation direction
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject go(Enums.Direction direction) {
        commonPage.go(direction);
        return this;
    }

    /**
     * Refreshes the current page.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject refresh() {
        commonPage.refresh();
        return this;
    }

    /**
     * Closes the current page/tab.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject close() {
        commonPage.close();
        return this;
    }

    /**
     * Quits the browser completely.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject quit() {
        commonPage.quit();
        return this;
    }

    /**
     * Sets the browser to full screen mode.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject fullScreen() {
        commonPage.fullScreen();
        return this;
    }

    /**
     * Maximises the browser window.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject maximise() {
        commonPage.maximise();
        return this;
    }

    // ========================
    // Logging
    // ========================

    /**
     * Logs the specified arguments to the console.
     *
     * @param args the arguments to log
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject log(String... args) {
        commonPage.log(args);
        return this;
    }

    /**
     * Logs a warning message.
     *
     * @param text the warning text
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject warn(String text) {
        commonPage.warn(text);
        return this;
    }

    // ========================
    // Focus/Element Location
    // ========================

    /**
     * Focuses on the specified target element.
     *
     * @param target the element to focus on
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject focus(Target target) {
        commonPage.focus(target);
        return this;
    }

    /**
     * Sets the focus wait time.
     *
     * @param waitTime the wait time in milliseconds
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject focus(int waitTime) {
        commonPage.focus(waitTime);
        return this;
    }

    /**
     * Focuses on the first available target from the list.
     *
     * @param targets the targets to search for
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject focus(Target... targets) {
        commonPage.focus(targets);
        return this;
    }

    /**
     * Focuses on the first available target from the list with a custom timeout.
     *
     * @param waitTime the wait time in milliseconds
     * @param targets  the targets to search for
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject focus(int waitTime, Target... targets) {
        commonPage.focus(waitTime, targets);
        return this;
    }

    // ========================
    // Peek
    // ========================

    /**
     * Checks if the target element is present on the page.
     *
     * @param target the element to check
     * @return true if present, false otherwise
     */
    public boolean peek(Target target) {
        return commonPage.peek(target);
    }

    /**
     * Checks if the target element is present with a custom timeout.
     *
     * @param target   the element to check
     * @param waitTime the wait time in milliseconds
     * @return true if present, false otherwise
     */
    public boolean peek(Target target, int waitTime) {
        return commonPage.peek(target, waitTime);
    }

    /**
     * Sets the peek wait time.
     *
     * @param waitTime the wait time in milliseconds
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject peek(int waitTime) {
        commonPage.peek(waitTime);
        return this;
    }

    // ========================
    // Probe
    // ========================

    /**
     * Probes for the target element's visibility.
     *
     * @param target the element to probe
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject probe(Target target) {
        commonPage.probe(target);
        return this;
    }

    /**
     * Probes for the target element with a specific state.
     *
     * @param target       the element to probe
     * @param elementState the expected state
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject probe(Target target, Enums.ElementState elementState) {
        commonPage.probe(target, elementState);
        return this;
    }

    /**
     * Sets the probe wait time.
     *
     * @param waitTime the wait time in milliseconds
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject probe(int waitTime) {
        commonPage.probe(waitTime);
        return this;
    }

    /**
     * Ends a conditional probe block.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject end() {
        commonPage.end();
        return this;
    }

    // ========================
    // Depart/Absent
    // ========================

    /**
     * Waits for the target element to disappear.
     *
     * @param target the element to wait for disappearance
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject depart(Target target) {
        commonPage.depart(target);
        return this;
    }

    /**
     * Waits for all targets in the list to disappear.
     *
     * @param targets the elements to wait for disappearance
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject depart(List<Target> targets) {
        commonPage.depart(targets);
        return this;
    }

    /**
     * Asserts that the target element is absent from the DOM.
     *
     * @param target the element that should be absent
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject absent(Target target) {
        commonPage.absent(target);
        return this;
    }

    /**
     * Returns to the document root.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject origin() {
        commonPage.origin();
        return this;
    }

    // ========================
    // Collections
    // ========================

    /**
     * Collects all elements matching the target selector.
     *
     * @param target the selector for elements to collect
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject collect(Target target) {
        commonPage.collect(target);
        return this;
    }

    /**
     * Collects elements from a list of targets.
     *
     * @param targets the list of targets to collect
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject collect(List<Target> targets) {
        commonPage.collect(targets);
        return this;
    }

    /**
     * Sets the collect timeout.
     *
     * @param waitTime the wait time in seconds
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject collect(int waitTime) {
        commonPage.collect(waitTime);
        return this;
    }

    /**
     * Returns the size of the current collection.
     *
     * @return the number of elements in the collection
     */
    public int size() {
        return commonPage.size();
    }

    // ========================
    // Choose
    // ========================

    /**
     * Chooses an element by index from the current collection.
     *
     * @param index the index (1-based) of the element to choose
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject choose(int index) {
        commonPage.choose(index);
        return this;
    }

    /**
     * Chooses an element by its visible text.
     *
     * @param text the text to match
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject choose(String text) {
        commonPage.choose(text);
        return this;
    }

    /**
     * Chooses an element by its visible text with optional lenient matching.
     *
     * @param text    the text to match
     * @param lenient whether to use partial matching
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject choose(String text, boolean lenient) {
        commonPage.choose(text, lenient);
        return this;
    }

    /**
     * Chooses the specified target element.
     *
     * @param target the element to choose
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject choose(Target target) {
        commonPage.choose(target);
        return this;
    }

    /**
     * Chooses an element by index enum.
     *
     * @param index the index enum (FIRST, SECOND, etc.)
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject choose(Enums.Index index) {
        commonPage.choose(index);
        return this;
    }

    /**
     * Chooses an element by list index enum.
     *
     * @param listIndex the list index enum (FIRST, LAST, NEXT, PREVIOUS)
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject choose(Enums.ListIndex listIndex) {
        commonPage.choose(listIndex);
        return this;
    }

    // ========================
    // Cognate
    // ========================

    /**
     * Finds a related element relative to an origin element.
     *
     * @param origin the starting element
     * @param target the related element to find
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject cognate(Target origin, Target target) {
        commonPage.cognate(origin, target);
        return this;
    }

    /**
     * Finds a related element relative to the current element.
     *
     * @param target the related element to find
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject cognate(Target target) {
        commonPage.cognate(target);
        return this;
    }

    // ========================
    // Element Interactions
    // ========================

    /**
     * Clicks the currently focused element.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject click() {
        commonPage.click();
        return this;
    }

    /**
     * Double-clicks the currently focused element.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject dblClick() {
        commonPage.dblClick();
        return this;
    }

    /**
     * Clears the currently focused element.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject clear() {
        commonPage.clear();
        return this;
    }

    /**
     * Hovers over the currently focused element.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject hover() {
        commonPage.hover();
        return this;
    }

    /**
     * Hovers over the specified target element.
     *
     * @param target the element to hover over
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject hover(Target target) {
        commonPage.hover(target);
        return this;
    }

    /**
     * Drags an element and drops it onto another.
     *
     * @param draggable the element to drag
     * @param dropZone  the element to drop onto
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject dragDrop(Target draggable, Target dropZone) {
        commonPage.dragDrop(draggable, dropZone);
        return this;
    }

    /**
     * Expands the specified target element.
     *
     * @param target the element to expand
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject expand(Target target) {
        commonPage.expand(target);
        return this;
    }

    // ========================
    // Keyboard Interaction
    // ========================

    /**
     * Types text into the currently focused element using the keyboard.
     * Each character is typed individually with key events.
     *
     * @param text the text to type
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject inputText(String text) {
        keyboard.compose(text);
        return this;
    }

    /**
     * Fills the currently focused element with text (clears first).
     *
     * @param text the text to fill
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject fill(String text) {
        keyboard.fill(text);
        return this;
    }

    /**
     * Presses a key using the keyboard.
     *
     * @param key the key to press
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject press(KeyInterface.KeyboardKey key) {
        keyboard.compose(key);
        return this;
    }

    /**
     * Presses a key combination.
     *
     * @param keys the key combination (e.g., "Control+A")
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject press(String keys) {
        keyboard.press(keys);
        return this;
    }

    // ========================
    // Assertions
    // ========================

    /**
     * Asserts the element's text matches exactly.
     *
     * @param text the expected text
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject matches(String text) {
        commonPage.matches(text);
        return this;
    }

    /**
     * Asserts the element's text contains the specified text.
     *
     * @param text the text to check for
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject contains(String text) {
        commonPage.contains(text);
        return this;
    }

    /**
     * Asserts that the specified text is present in the collection.
     *
     * @param text the text to check for
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject present(String text) {
        commonPage.present(text);
        return this;
    }

    /**
     * Asserts that the specified text is absent from the collection.
     *
     * @param text the text that should not be present
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject absent(String text) {
        commonPage.absent(text);
        return this;
    }

    /**
     * Asserts the element is selected.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject selected() {
        commonPage.selected();
        return this;
    }

    /**
     * Asserts the element is not selected.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject unSelected() {
        commonPage.unSelected();
        return this;
    }

    /**
     * Asserts the element is enabled.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject enabled() {
        commonPage.enabled();
        return this;
    }

    /**
     * Asserts the element is disabled.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject disabled() {
        commonPage.disabled();
        return this;
    }

    /**
     * Asserts the element is clickable.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject clickable() {
        commonPage.clickable();
        return this;
    }

    /**
     * Asserts the element is not clickable.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject unclickable() {
        commonPage.unclickable();
        return this;
    }

    /**
     * Asserts the element is visible.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject visible() {
        commonPage.visible();
        return this;
    }

    /**
     * Asserts the element is hidden.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject hidden() {
        commonPage.hidden();
        return this;
    }

    /**
     * Asserts the element is editable.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject editable() {
        commonPage.editable();
        return this;
    }

    // ========================
    // Get Element Properties
    // ========================

    /**
     * Gets the specified trait of the current element.
     *
     * @param elementTrait the trait to retrieve
     * @return the value of the trait
     */
    public String get(Enums.ElementTrait elementTrait) {
        return commonPage.get(elementTrait);
    }

    /**
     * Gets the current page URL.
     *
     * @return the URL as a string
     */
    public String getUrl() {
        return commonPage.getUrl();
    }

    /**
     * Gets the current page title.
     *
     * @return the title as a string
     */
    public String getTitle() {
        return commonPage.getTitle();
    }

    // ========================
    // Frames
    // ========================

    /**
     * Switches to a frame by ID or name.
     *
     * @param idOrName the frame ID or name
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject frame(String idOrName) {
        commonPage.frame(idOrName);
        return this;
    }

    /**
     * Switches to a frame by target.
     *
     * @param target the frame element
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject frame(Target target) {
        commonPage.frame(target);
        return this;
    }

    /**
     * Switches to a frame by index.
     *
     * @param index the frame index
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject frame(int index) {
        commonPage.frame(index);
        return this;
    }

    /**
     * Returns to the default content (main frame).
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject frame() {
        commonPage.frame();
        return this;
    }

    /**
     * Exits the current frame.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject deframe() {
        commonPage.deframe();
        return this;
    }

    // ========================
    // Windows
    // ========================

    /**
     * Switches to the specified window.
     *
     * @param window the window enum
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject window(Enums.Window window) {
        commonPage.window(window);
        return this;
    }

    /**
     * Switches to a window by index.
     *
     * @param index the window index
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject window(int index) {
        commonPage.window(index);
        return this;
    }

    // ========================
    // Storage
    // ========================

    /**
     * Stores a value with the specified key.
     *
     * @param key   the key to store
     * @param value the value to store
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject store(String key, String value) {
        commonPage.store(key, value);
        return this;
    }

    /**
     * Retrieves a stored value by key.
     *
     * @param key the key to retrieve
     * @return the stored value
     */
    public String retrieve(String key) {
        return commonPage.retrieve(key);
    }

    /**
     * Stores the currently focused element.
     *
     * @param key the key to store with
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject storeFocused(String key) {
        commonPage.storeFocused(key);
        return this;
    }

    /**
     * Retrieves a previously stored element.
     *
     * @param key the key to retrieve
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject retrieveFocused(String key) {
        commonPage.retrieveFocused(key);
        return this;
    }

    // ========================
    // Utility
    // ========================

    /**
     * Resets the page state and timeouts.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject reset() {
        commonPage.reset();
        return this;
    }

    /**
     * Pauses execution for the specified time.
     *
     * @param seconds the time to pause in seconds
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject pause(int seconds) {
        commonPage.pause(seconds);
        return this;
    }

    /**
     * Pauses execution for the specified time.
     *
     * @param milliSeconds the time to pause in milliseconds
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject pause(long milliSeconds) {
        commonPage.pause(milliSeconds);
        return this;
    }

    /**
     * Takes a screenshot.
     *
     * @param filename the filename for the screenshot
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject screenshot(String filename) {
        commonPage.screenshot(filename);
        return this;
    }

    /**
     * Prints the currently focused element to the console.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject printFocused() {
        commonPage.printFocused();
        return this;
    }

    /**
     * Prints the current collection to the console.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject printCollection() {
        commonPage.printCollection();
        return this;
    }

    // ========================
    // Performable
    // ========================

    /**
     * Performs a custom action defined by a Performable.
     *
     * @param performable the performable action to execute
     * @param <T>         the type of performable
     * @return this CommonPageObject for method chaining
     */
    public <T extends Performable> CommonPageObject perform(T performable) {
        commonPage.perform(performable);
        return this;
    }

    // ========================
    // Events
    // ========================

    /**
     * Triggers an event on the current element.
     *
     * @param event the event to trigger
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject trigger(Enums.Event event) {
        commonPage.trigger(event);
        return this;
    }

    /**
     * Executes JavaScript in the browser.
     *
     * @param script the JavaScript to execute
     * @return the result of the script
     */
    public String javascript(String script) {
        return commonPage.javascript(script);
    }

    // ========================
    // Playwright Specific
    // ========================

    /**
     * Waits for a network response matching the URL pattern.
     *
     * @param urlPattern the URL pattern to match
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject waitForResponse(String urlPattern) {
        commonPage.waitForResponse(urlPattern);
        return this;
    }

    /**
     * Waits for navigation to complete.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject waitForNavigation() {
        commonPage.waitForNavigation();
        return this;
    }

    /**
     * Waits for the page to load completely.
     *
     * @return this CommonPageObject for method chaining
     */
    public CommonPageObject waitForLoadState() {
        commonPage.waitForLoadState();
        return this;
    }
}

