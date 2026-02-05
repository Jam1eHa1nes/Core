package com.playwright.qa.automation.core;

import com.playwright.qa.automation.core.locators.Target;

/**
 * Interface for navigation operations in Playwright.
 */
public interface NavigationInterface {

    /**
     * Navigates to the leaf element (deepest child).
     */
    void leaf();

    /**
     * Scrolls to the specified target element.
     *
     * @param target the element to scroll to
     */
    void scroll(Target target);

    /**
     * Scrolls to the currently focused element.
     */
    void scroll();

    /**
     * Descends to the first child element.
     */
    void descend();

    /**
     * Descends to a specific child element.
     *
     * @param target the child element to descend to
     */
    void descend(Target target);

    /**
     * Ascends to the parent element.
     */
    void ascend();

    /**
     * Ascends by a specified number of levels.
     *
     * @param level the number of levels to ascend
     */
    void ascend(Enums.Level level);

    /**
     * Moves to the next sibling element.
     */
    void sibling();

    /**
     * Moves to a sibling matching the target.
     *
     * @param target the sibling element to find
     */
    void sibling(Target target);
}

