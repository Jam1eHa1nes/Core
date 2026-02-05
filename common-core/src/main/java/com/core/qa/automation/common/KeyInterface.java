package com.core.qa.automation.common;

/**
 * Interface for keyboard interactions.
 * Provides methods for typing, pressing keys, and handling key combinations.
 * <p>
 * This interface is implemented by both Selenium and Playwright keyboard classes.
 */
public interface KeyInterface {

    /**
     * Types text into the currently focused element.
     *
     * @param content the text to type
     */
    void compose(String content);

    /**
     * Presses a special key.
     *
     * @param key the key to press (use Key enum)
     */
    void compose(Key key);

    /**
     * Presses a special key multiple times.
     *
     * @param key    the key to press
     * @param repeat number of times to repeat
     */
    void compose(Key key, int repeat);

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
    void hold(Key key);

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
    void release(Key key);

    /**
     * Submits the current form.
     */
    void submit();

    /**
     * Clears the current element and types new text.
     *
     * @param text the text to fill
     */
    void fill(String text);

    /**
     * Presses a key combination (e.g., "Control+A").
     *
     * @param keys the key combination string
     */
    void press(String keys);

    /**
     * Enum for common keyboard keys.
     * Maps to both Selenium Keys and Playwright keyboard keys.
     */
    enum Key {
        // Navigation
        ENTER("Enter", "\uE007"),
        TAB("Tab", "\uE004"),
        ESCAPE("Escape", "\uE00C"),
        BACKSPACE("Backspace", "\uE003"),
        DELETE("Delete", "\uE017"),
        
        // Arrow keys
        ARROW_UP("ArrowUp", "\uE013"),
        ARROW_DOWN("ArrowDown", "\uE015"),
        ARROW_LEFT("ArrowLeft", "\uE012"),
        ARROW_RIGHT("ArrowRight", "\uE014"),
        
        // Page navigation
        HOME("Home", "\uE011"),
        END("End", "\uE010"),
        PAGE_UP("PageUp", "\uE00E"),
        PAGE_DOWN("PageDown", "\uE00F"),
        
        // Function keys
        F1("F1", "\uE031"),
        F2("F2", "\uE032"),
        F3("F3", "\uE033"),
        F4("F4", "\uE034"),
        F5("F5", "\uE035"),
        F6("F6", "\uE036"),
        F7("F7", "\uE037"),
        F8("F8", "\uE038"),
        F9("F9", "\uE039"),
        F10("F10", "\uE03A"),
        F11("F11", "\uE03B"),
        F12("F12", "\uE03C"),
        
        // Modifier keys
        CONTROL("Control", "\uE009"),
        ALT("Alt", "\uE00A"),
        SHIFT("Shift", "\uE008"),
        META("Meta", "\uE03D"),
        
        // Other
        SPACE(" ", "\uE00D"),
        INSERT("Insert", "\uE016"),
        NULL("", "\uE000"),
        CANCEL("Cancel", "\uE001"),
        HELP("Help", "\uE002"),
        CLEAR("Clear", "\uE005"),
        PAUSE("Pause", "\uE00B"),
        SEMICOLON(";", "\uE018"),
        EQUALS("=", "\uE019"),
        NUMPAD0("0", "\uE01A"),
        NUMPAD1("1", "\uE01B"),
        NUMPAD2("2", "\uE01C"),
        NUMPAD3("3", "\uE01D"),
        NUMPAD4("4", "\uE01E"),
        NUMPAD5("5", "\uE01F"),
        NUMPAD6("6", "\uE020"),
        NUMPAD7("7", "\uE021"),
        NUMPAD8("8", "\uE022"),
        NUMPAD9("9", "\uE023"),
        MULTIPLY("*", "\uE024"),
        ADD("+", "\uE025"),
        SEPARATOR(",", "\uE026"),
        SUBTRACT("-", "\uE027"),
        DECIMAL(".", "\uE028"),
        DIVIDE("/", "\uE029");

        private final String playwrightKey;
        private final String seleniumKey;

        Key(String playwrightKey, String seleniumKey) {
            this.playwrightKey = playwrightKey;
            this.seleniumKey = seleniumKey;
        }

        /**
         * Gets the Playwright key string.
         *
         * @return Playwright key representation
         */
        public String getPlaywrightKey() {
            return playwrightKey;
        }

        /**
         * Gets the Selenium key string (Unicode).
         *
         * @return Selenium key representation
         */
        public String getSeleniumKey() {
            return seleniumKey;
        }

        /**
         * Gets the key value based on framework.
         *
         * @param isPlaywright true for Playwright, false for Selenium
         * @return the appropriate key string
         */
        public String getValue(boolean isPlaywright) {
            return isPlaywright ? playwrightKey : seleniumKey;
        }
    }
}

