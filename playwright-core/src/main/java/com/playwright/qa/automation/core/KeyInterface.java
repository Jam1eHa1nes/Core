package com.playwright.qa.automation.core;

import com.playwright.qa.automation.core.locators.Target;

/**
 * Interface for keyboard interactions in Playwright.
 */
public interface KeyInterface {

    /**
     * Types text into the currently focused element.
     *
     * @param keysToSend the text to type
     */
    void compose(String keysToSend);

    /**
     * Presses a special key.
     *
     * @param key the key to press
     */
    void compose(KeyboardKey key);

    /**
     * Presses a special key multiple times.
     *
     * @param key    the key to press
     * @param repeat number of times to repeat
     */
    void compose(KeyboardKey key, int repeat);

    /**
     * Holds down a key.
     *
     * @param key the key to hold
     */
    void hold(String key);

    /**
     * Holds down a special key.
     *
     * @param key the key to hold
     */
    void hold(KeyboardKey key);

    /**
     * Releases a held key.
     *
     * @param key the key to release
     */
    void release(String key);

    /**
     * Releases a held special key.
     *
     * @param key the key to release
     */
    void release(KeyboardKey key);

    /**
     * Submits the form.
     */
    void submit();

    /**
     * Clears the current element and types new text.
     *
     * @param text the text to type
     */
    void fill(String text);

    /**
     * Presses a key combination.
     *
     * @param keys the key combination (e.g., "Control+A")
     */
    void press(String keys);

    /**
     * Enum for common keyboard keys in Playwright.
     */
    enum KeyboardKey {
        ENTER("Enter"),
        TAB("Tab"),
        ESCAPE("Escape"),
        BACKSPACE("Backspace"),
        DELETE("Delete"),
        ARROW_UP("ArrowUp"),
        ARROW_DOWN("ArrowDown"),
        ARROW_LEFT("ArrowLeft"),
        ARROW_RIGHT("ArrowRight"),
        HOME("Home"),
        END("End"),
        PAGE_UP("PageUp"),
        PAGE_DOWN("PageDown"),
        F1("F1"),
        F2("F2"),
        F3("F3"),
        F4("F4"),
        F5("F5"),
        F6("F6"),
        F7("F7"),
        F8("F8"),
        F9("F9"),
        F10("F10"),
        F11("F11"),
        F12("F12"),
        CONTROL("Control"),
        ALT("Alt"),
        SHIFT("Shift"),
        META("Meta"),
        SPACE(" ");

        private final String value;

        KeyboardKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

