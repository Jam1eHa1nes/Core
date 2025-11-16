package core.ui;

/**
 * Framework-agnostic UI actions that can be implemented by Selenium or Playwright.
 */
public interface UiActions {
    /** Open the given URL in the current browser context. */
    void open(String url);

    /** Click an element located by the given target (locator). */
    void click(Target target);

    /** Click the current element context (requires prior focus()). */
    void click();

    /** Type the given text into the element located by the given target. */
    void compose(Target target, String text);

    /** Type into the current element context (requires prior focus()). */
    void compose(String text);

    /** Set focus to the element located by the given target. */
    void focus(Target target);

    /** Get text content of element located by the given target. */
    String getText(Target target);

    /** Get text content of the current element context. */
    String getText();

    /** Close and cleanup underlying resources. */
    void close();

    // --- Additional cross-framework capabilities ---

    /**
     * Check if at least one element exists for the given selector.
     */
    boolean exists(Target target);

    /** Check existence for the current element context. */
    boolean exists();

    /**
     * Returns true if the element is present and visible to the user.
     */
    boolean isVisible(Target target);

    /** Returns true if the current element context is visible. */
    boolean isVisible();

    /**
     * Wait until element becomes visible or timeout (in milliseconds) is reached.
     * Implementations should throw a runtime exception on timeout to fail fast.
     */
    void waitForVisible(Target target, long timeoutMs);

    /** Wait until the current element context becomes visible. */
    void waitForVisible(long timeoutMs);

    /**
     * Read the current value of an input/textarea/select element. Implementations may fall back to text.
     */
    String value(Target target);

    /** Read the current value of the current element context. */
    String value();

    /**
     * Get the specified attribute value of the element, or null if not present.
     */
    String attribute(Target target, String name);

    /** Get an attribute value from the current element context. */
    String attribute(String name);

    /** Hover over the element. */
    void hover(Target target);

    /** Hover over the current element context. */
    void hover();

    /** Navigate back in history. */
    void back();

    /** Get current page title. */
    String title();

    /** Get current page URL. */
    String url();

    /** Take a screenshot and save to the given filesystem path. */
    void screenshot(String path);

    // ---- Newly standardized cross-framework utilities ----

    /** Reload the current page. */
    void refresh();

    /** Navigate forward in history, if possible. */
    void forward();

    /** Clear the value of an input-like element. */
    void clear(Target target);

    /** Clear the current element context. */
    void clear();

    /** Double-click the element. */
    void doubleClick(Target target);

    /** Double-click the current element context. */
    void doubleClick();

    /** Select an option from a select element by visible text (label). */
    void selectByText(Target target, String text);

    /** Select option by text for the current element context. */
    void selectByText(String text);

    /** Select an option from a select element by value. */
    void selectByValue(Target target, String value);

    /** Select option by value for the current element context. */
    void selectByValue(String value);

    /** Wait until the element is hidden or detached. */
    void waitForHidden(Target target, long timeoutMs);

    /** Wait until the current element context is hidden. */
    void waitForHidden(long timeoutMs);

    /** Scroll the element into view if needed. */
    void scrollIntoView(Target target);

    /** Scroll the current element context into view. */
    void scrollIntoView();

    /** Press a key or key combination on the element (e.g., "Control+A"). */
    void press(Target target, String key);

    /** Press a key on the current element context. */
    void press(String key);

    /** Press one or more key sequences on the element. */
    void press(Target target, CharSequence... keys);

    /** Press one or more keys on the current element context. */
    void press(CharSequence... keys);

    /** Set the checked state of a checkbox-like element. */
    void setChecked(Target target, boolean checked);

    /** Set checked state on the current element context. */
    void setChecked(boolean checked);

    /** Upload a file to an input[type=file]. */
    void uploadFile(Target target, String path);

    /** Upload a file using the current element context. */
    void uploadFile(String path);
}
