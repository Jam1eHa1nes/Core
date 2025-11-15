package core.ui;

/**
 * Framework-agnostic UI actions that can be implemented by Selenium or Playwright.
 */
public interface UiActions {
    /** Open the given URL in the current browser context. */
    void open(String url);

    /** Click an element located by the given selector (CSS by default). */
    void click(String selector);

    /** Type the given text into the element located by the given selector. */
    void compose(String selector, String text);

    /** Set focus to the element located by the given selector. */
    void focus(String selector);

    /** Get text content of element located by the given selector. */
    String getText(String selector);

    /** Close and cleanup underlying resources. */
    void close();

    // --- Additional cross-framework capabilities ---

    /**
     * Check if at least one element exists for the given selector.
     */
    boolean exists(String selector);

    /**
     * Returns true if the element is present and visible to the user.
     */
    boolean isVisible(String selector);

    /**
     * Wait until element becomes visible or timeout (in milliseconds) is reached.
     * Implementations should throw a runtime exception on timeout to fail fast.
     */
    void waitForVisible(String selector, long timeoutMs);

    /**
     * Read the current value of an input/textarea/select element. Implementations may fall back to text.
     */
    String value(String selector);

    /**
     * Get the specified attribute value of the element, or null if not present.
     */
    String attribute(String selector, String name);

    /** Hover over the element. */
    void hover(String selector);

    /** Navigate back in history. */
    void back();

    /** Get current page title. */
    String title();

    /** Get current page URL. */
    String url();

    /** Take a screenshot and save to the given filesystem path. */
    void screenshot(String path);
}
