package com.playwright.qa.automation.core;

import com.microsoft.playwright.PlaywrightException;

/**
 * Keyboard class for handling keyboard interactions in Playwright.
 * Extends CommonPage to access the page and current element.
 */
public class Keyboard extends CommonPage implements KeyInterface {

    private static Keyboard instance = null;

    public static synchronized Keyboard getInstance() {
        if (instance == null) {
            instance = new Keyboard();
        }
        return instance;
    }

    @Override
    public void compose(String keysToSend) {
        if (execute) {
            String attribute = currentElement.getAttribute("type");
            if (attribute == null || !attribute.equals("password")) {
                log("compose()", "Text: " + keysToSend);
            } else {
                int stringSize = keysToSend.length();
                log("compose()", "Text: " + "*".repeat(stringSize));
            }
            try {
                currentElement.type(keysToSend);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("compose() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void compose(KeyboardKey key) {
        if (execute) {
            log("compose()", "Key: " + key.name());
            try {
                currentElement.press(key.getValue());
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("compose() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void compose(KeyboardKey key, int repeat) {
        if (execute) {
            log("compose()", "Key: " + key.name() + " Repeat: " + repeat);
            for (int i = 0; i < repeat; i++) {
                compose(key);
            }
        }
    }

    @Override
    public void hold(String key) {
        if (execute) {
            log("hold()", "Key: " + key);
            try {
                page.keyboard().down(key);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("hold() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void hold(KeyboardKey key) {
        if (execute) {
            log("hold()", "Key: " + key.name());
            try {
                page.keyboard().down(key.getValue());
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("hold() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void release(String key) {
        if (execute) {
            log("release()", "Key: " + key);
            try {
                page.keyboard().up(key);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("release() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void release(KeyboardKey key) {
        if (execute) {
            log("release()", "Key: " + key.name());
            try {
                page.keyboard().up(key.getValue());
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("release() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void submit() {
        if (execute) {
            log("submit()", "Submitting form");
            try {
                currentElement.press("Enter");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("submit() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void fill(String text) {
        if (execute) {
            String attribute = currentElement.getAttribute("type");
            if (attribute == null || !attribute.equals("password")) {
                log("fill()", "Text: " + text);
            } else {
                int stringSize = text.length();
                log("fill()", "Text: " + "*".repeat(stringSize));
            }
            try {
                currentElement.fill(text);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("fill() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void press(String keys) {
        if (execute) {
            log("press()", "Keys: " + keys);
            try {
                page.keyboard().press(keys);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("press() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Types text with specified delay between keystrokes.
     *
     * @param text  the text to type
     * @param delay delay between keystrokes in milliseconds
     */
    public void typeWithDelay(String text, int delay) {
        if (execute) {
            log("typeWithDelay()", "Text: " + text + " Delay: " + delay + "ms");
            try {
                page.keyboard().type(text, new com.microsoft.playwright.Keyboard.TypeOptions().setDelay(delay));
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("typeWithDelay() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Inserts text at the current cursor position without triggering input events.
     *
     * @param text the text to insert
     */
    public void insertText(String text) {
        if (execute) {
            log("insertText()", "Text: " + text);
            try {
                page.keyboard().insertText(text);
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("insertText() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Selects all text in the current element.
     */
    public void selectAll() {
        if (execute) {
            log("selectAll()", "Selecting all text");
            try {
                page.keyboard().press("Control+a");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("selectAll() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Copies the selected text.
     */
    public void copy() {
        if (execute) {
            log("copy()", "Copying text");
            try {
                page.keyboard().press("Control+c");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("copy() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Pastes from clipboard.
     */
    public void paste() {
        if (execute) {
            log("paste()", "Pasting text");
            try {
                page.keyboard().press("Control+v");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("paste() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Cuts the selected text.
     */
    public void cut() {
        if (execute) {
            log("cut()", "Cutting text");
            try {
                page.keyboard().press("Control+x");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("cut() failed: " + e.getMessage());
            }
        }
    }
}

