package com.core.qa.automation.common;

/**
 * Common enums used by both Playwright and Selenium frameworks.
 * These enums provide a framework-agnostic way to specify browser types,
 * navigation directions, element states, and other common values.
 */
public final class FrameworkEnums {

    private FrameworkEnums() {
        // Utility class
    }

    /**
     * Supported browser types.
     */
    public enum Browser {
        CHROME,
        CHROMIUM,
        FIREFOX,
        WEBKIT,
        SAFARI,
        EDGE
    }

    /**
     * Navigation directions.
     */
    public enum Direction {
        FORWARD,
        BACK
    }

    /**
     * Element states for assertions and waits.
     */
    public enum ElementState {
        VISIBLE,
        HIDDEN,
        ENABLED,
        DISABLED,
        SELECTED,
        CLICKABLE,
        PRESENT,
        ABSENT,
        EDITABLE
    }

    /**
     * Element traits/attributes to retrieve.
     */
    public enum ElementTrait {
        TEXT,
        VALUE,
        INNER_HTML,
        OUTER_HTML,
        TAG_NAME,
        CLASS,
        ID,
        NAME,
        HREF,
        SRC,
        TITLE,
        PLACEHOLDER,
        TYPE,
        CHECKED,
        SELECTED,
        DISABLED,
        READONLY
    }

    /**
     * Index selectors for collections.
     */
    public enum Index {
        FIRST,
        LAST,
        RANDOM
    }

    /**
     * List navigation selectors.
     */
    public enum ListIndex {
        NEXT,
        PREVIOUS
    }

    /**
     * Window/tab selectors.
     */
    public enum Window {
        MAIN,
        NEW,
        PREVIOUS,
        NEXT
    }

    /**
     * Content display options.
     */
    public enum Content {
        DISPLAYED,
        HIDDEN
    }

    /**
     * DOM events.
     */
    public enum Event {
        CLICK,
        DOUBLE_CLICK,
        MOUSE_OVER,
        MOUSE_OUT,
        MOUSE_DOWN,
        MOUSE_UP,
        FOCUS,
        BLUR,
        CHANGE,
        INPUT,
        SUBMIT,
        KEYDOWN,
        KEYUP,
        KEYPRESS
    }

    /**
     * Alert actions.
     */
    public enum AlertAction {
        ACCEPT,
        DISMISS
    }

    /**
     * Wait types.
     */
    public enum WaitType {
        IMPLICIT,
        EXPLICIT,
        FLUENT
    }
}
