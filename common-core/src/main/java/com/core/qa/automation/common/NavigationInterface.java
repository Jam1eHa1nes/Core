package com.core.qa.automation.common;

/**
 * Interface for navigation operations in the DOM tree.
 * Provides methods for scrolling, ascending, descending, and traversing the DOM.
 * <p>
 * This interface is implemented by both Selenium and Playwright navigation classes.
 */
public interface NavigationInterface {

    /**
     * Navigates to the leaf element (deepest child) from the current position.
     */
    void leaf();

    /**
     * Scrolls the page to bring the currently focused element into view.
     */
    void scroll();

    /**
     * Descends to the first child element from the current position.
     */
    void descend();

    /**
     * Ascends to the parent element from the current position.
     */
    void ascend();

    /**
     * Ascends by a specified number of levels in the DOM tree.
     *
     * @param levels the number of levels to ascend
     */
    void ascend(int levels);

    /**
     * Traverses to the next sibling element.
     */
    void traverse();

    /**
     * Traverses to a sibling at the specified index.
     *
     * @param index the sibling index
     */
    void traverse(int index);

    /**
     * Reverses the traversal direction (goes to previous sibling).
     */
    void reverse();

    /**
     * Reverses to a sibling at the specified index.
     *
     * @param index the sibling index
     */
    void reverse(int index);

    /**
     * Moves to the next sibling element.
     */
    void sibling();
}

