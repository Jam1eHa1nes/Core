package com.playwright.qa.automation.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
import com.playwright.qa.automation.core.locators.Target;

/**
 * Navigation class for DOM traversal and scrolling in Playwright.
 * Extends CommonPage to access the page and current element.
 */
public class Navigation extends CommonPage implements NavigationInterface {

    private static Navigation instance = null;

    public static synchronized Navigation getInstance() {
        if (instance == null) {
            instance = new Navigation();
        }
        return instance;
    }

    @Override
    public void leaf() {
        if (execute) {
            log("leaf()", "Navigating to leaf element");
            try {
                // Navigate to the deepest child
                while (true) {
                    Locator child = currentElement.locator(":first-child");
                    if (child.count() == 0) {
                        break;
                    }
                    currentElement = child.first();
                }
            } catch (PlaywrightException e) {
                // Reached the leaf
            }
        }
    }

    @Override
    public void scroll(Target target) {
        if (execute) {
            log("scroll()", target.toString());
            focus(target);
            scrollIntoView();
        }
    }

    @Override
    public void scroll() {
        if (execute) {
            log("scroll()", "Scrolling to current element");
            scrollIntoView();
        }
    }

    private void scrollIntoView() {
        try {
            currentElement.scrollIntoViewIfNeeded();
        } catch (PlaywrightException e) {
            takeScreenShotAndExit("scrollIntoView() failed: " + e.getMessage());
        }
    }

    @Override
    public void descend() {
        if (execute) {
            log("descend()", "To first child");
            try {
                currentElement = currentElement.locator(":first-child").first();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("descend() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void descend(Target target) {
        if (execute) {
            log("descend()", target.toString());
            try {
                currentElement = currentElement.locator(target.getSelector()).first();
                currentSelector = target.getSelector();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("descend() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void ascend() {
        if (execute) {
            log("ascend()", "To parent");
            try {
                currentElement = currentElement.locator("xpath=..");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("ascend() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void ascend(Enums.Level level) {
        if (execute) {
            log("ascend()", "Level: " + level.getValue());
            try {
                StringBuilder xpathBuilder = new StringBuilder("xpath=");
                for (int i = 0; i < level.getValue(); i++) {
                    xpathBuilder.append("..");
                    if (i < level.getValue() - 1) {
                        xpathBuilder.append("/");
                    }
                }
                currentElement = currentElement.locator(xpathBuilder.toString());
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("ascend() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void sibling() {
        if (execute) {
            log("sibling()", "To next sibling");
            try {
                currentElement = currentElement.locator("xpath=following-sibling::*[1]");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("sibling() failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void sibling(Target target) {
        if (execute) {
            log("sibling()", target.toString());
            try {
                currentElement = currentElement.locator("xpath=following-sibling::" + extractTagFromSelector(target.getSelector()));
                currentSelector = target.getSelector();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("sibling() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Scrolls to the top of the page.
     */
    public void scrollToTop() {
        if (execute) {
            log("scrollToTop()", "Scrolling to top");
            page.evaluate("window.scrollTo(0, 0)");
        }
    }

    /**
     * Scrolls to the bottom of the page.
     */
    public void scrollToBottom() {
        if (execute) {
            log("scrollToBottom()", "Scrolling to bottom");
            page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        }
    }

    /**
     * Scrolls by a specified amount.
     *
     * @param x horizontal scroll amount
     * @param y vertical scroll amount
     */
    public void scrollBy(int x, int y) {
        if (execute) {
            log("scrollBy()", "X: " + x + " Y: " + y);
            page.evaluate("window.scrollBy(" + x + ", " + y + ")");
        }
    }

    /**
     * Moves to the previous sibling.
     */
    public void previousSibling() {
        if (execute) {
            log("previousSibling()", "To previous sibling");
            try {
                currentElement = currentElement.locator("xpath=preceding-sibling::*[1]");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("previousSibling() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Navigates to the nth child.
     *
     * @param n the child index (1-based)
     */
    public void nthChild(int n) {
        if (execute) {
            log("nthChild()", "Child: " + n);
            try {
                currentElement = currentElement.locator(":nth-child(" + n + ")");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("nthChild() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Navigates to the last child.
     */
    public void lastChild() {
        if (execute) {
            log("lastChild()", "To last child");
            try {
                currentElement = currentElement.locator(":last-child");
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("lastChild() failed: " + e.getMessage());
            }
        }
    }

    /**
     * Finds an ancestor matching the target.
     *
     * @param target the ancestor to find
     */
    public void ancestor(Target target) {
        if (execute) {
            log("ancestor()", target.toString());
            try {
                currentElement = currentElement.locator("xpath=ancestor::" + extractTagFromSelector(target.getSelector()));
                currentSelector = target.getSelector();
            } catch (PlaywrightException e) {
                takeScreenShotAndExit("ancestor() failed: " + e.getMessage());
            }
        }
    }

    private String extractTagFromSelector(String selector) {
        // Simple extraction for basic selectors
        if (selector.startsWith("#") || selector.startsWith(".") || selector.startsWith("[")) {
            return "*" + selector;
        }
        return selector;
    }
}

