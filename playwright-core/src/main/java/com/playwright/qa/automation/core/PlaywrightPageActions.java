package com.playwright.qa.automation.core;

import com.core.qa.automation.common.FrameworkEnums;
import com.core.qa.automation.common.PageActions;
import com.core.qa.automation.common.locators.Locator;
import com.playwright.qa.automation.core.locators.Target;

import java.util.List;

/**
 * Playwright implementation of the framework-agnostic PageActions interface.
 * This adapter wraps the existing CommonPage/CommonPageObject functionality.
 * <p>
 * <b>Usage:</b>
 * <pre>
 *     PageActions page = new PlaywrightPageActions();
 *     page.open(Browser.CHROMIUM)
 *         .go("https://example.com")
 *         .focus(LocatorFactory.id("search"))
 *         .type("test");
 * </pre>
 */
public class PlaywrightPageActions implements PageActions {

    private final CommonPage commonPage;

    public PlaywrightPageActions() {
        this.commonPage = CommonPage.getInstance();
    }

    // ========================
    // Helper: Convert Locator to Target
    // ========================

    private Target toTarget(Locator locator) {
        if (locator == null) {
            throw new IllegalArgumentException("Locator cannot be null");
        }

        String value = locator.getValue();
        String secondary = locator.getSecondaryValue();
        
        // Build a Playwright-compatible selector based on locator type
        switch (locator.getType()) {
            case ID:
                return new Target("#" + value, value, Target.Type.ID);
            case CSS:
                return new Target(value, value, Target.Type.CSS);
            case XPATH:
                return new Target("xpath=" + value, value, Target.Type.XPATH);
            case NAME:
                return new Target("[name=\"" + value + "\"]", value, Target.Type.NAME);
            case CLASS:
                return new Target("." + value, value, Target.Type.CLASS);
            case TAG:
                return new Target(value, value, Target.Type.TAG);
            case TEXT:
                return new Target("text=\"" + value + "\"", value, Target.Type.TEXT);
            case PARTIAL_TEXT:
                return new Target("text=" + value, value, Target.Type.PARTIAL_TEXT);
            case LINK_TEXT:
                return new Target("a:has-text(\"" + value + "\")", value, Target.Type.LINK_TEXT);
            case PARTIAL_LINK_TEXT:
                return new Target("a:has-text(\"" + value + "\")", value, Target.Type.PARTIAL_LINK_TEXT);
            case PLACEHOLDER:
                return new Target("[placeholder=\"" + value + "\"]", value, Target.Type.PLACEHOLDER);
            case TITLE:
                return new Target("[title=\"" + value + "\"]", value, Target.Type.TITLE);
            case ROLE:
                return new Target("role=" + value, value, Target.Type.ROLE);
            case DATA_TEST_ID:
                return new Target("[data-testid=\"" + value + "\"]", value, Target.Type.DATA_TEST_ID);
            case DATA_ATTRIBUTE:
                return new Target("[data-" + secondary + "=\"" + value + "\"]", secondary, value, Target.Type.DATA_ATTRIBUTE);
            case TAG_WITH_TEXT:
                return new Target(secondary + ":has-text(\"" + value + "\")", secondary, value, Target.Type.TAG_WITH_TEXT);
            case TAG_CONTAINS_TEXT:
                return new Target(secondary + ":has-text(\"" + value + "\")", secondary, value, Target.Type.TAG_CONTAINS_TEXT);
            case LABEL:
                return new Target("label:has-text(\"" + value + "\")", value, Target.Type.LABEL);
            case VALUE:
                return new Target("[value=\"" + value + "\"]", value, Target.Type.VALUE);
            case ATTRIBUTE:
                return new Target("[" + secondary + "=\"" + value + "\"]", secondary, value, Target.Type.ATTRIBUTE);
            default:
                // Default to CSS selector
                return new Target(value, value, Target.Type.CSS);
        }
    }

    private Enums.Browser mapBrowser(FrameworkEnums.Browser browser) {
        switch (browser) {
            case CHROME:
            case CHROMIUM: return Enums.Browser.CHROMIUM;
            case FIREFOX: return Enums.Browser.FIREFOX;
            case WEBKIT:
            case SAFARI: return Enums.Browser.WEBKIT;
            case EDGE: return Enums.Browser.CHROMIUM; // Edge uses Chromium
            default: return Enums.Browser.CHROMIUM;
        }
    }

    private Enums.Direction mapDirection(FrameworkEnums.Direction direction) {
        switch (direction) {
            case FORWARD: return Enums.Direction.FORWARD;
            case BACK: return Enums.Direction.BACK;
            default: return Enums.Direction.BACK;
        }
    }

    private Enums.ElementTrait mapTrait(FrameworkEnums.ElementTrait trait) {
        switch (trait) {
            case TEXT: return Enums.ElementTrait.TEXT;
            case VALUE: return Enums.ElementTrait.VALUE;
            case CLASS: return Enums.ElementTrait.CLASS;
            case ID: return Enums.ElementTrait.ID;
            case NAME: return Enums.ElementTrait.NAME;
            case HREF: return Enums.ElementTrait.HREF;
            case TITLE: return Enums.ElementTrait.TITLE;
            case PLACEHOLDER: return Enums.ElementTrait.PLACEHOLDER;
            default: return Enums.ElementTrait.TEXT;
        }
    }

    // ========================
    // Browser Management
    // ========================

    @Override
    public PageActions open() {
        commonPage.open();
        return this;
    }

    @Override
    public PageActions open(FrameworkEnums.Browser browser) {
        commonPage.open(mapBrowser(browser));
        return this;
    }

    @Override
    public PageActions go(String url) {
        commonPage.go(url);
        return this;
    }

    @Override
    public PageActions go(FrameworkEnums.Direction direction) {
        commonPage.go(mapDirection(direction));
        return this;
    }

    @Override
    public PageActions refresh() {
        commonPage.refresh();
        return this;
    }

    @Override
    public PageActions close() {
        commonPage.close();
        return this;
    }

    @Override
    public void quit() {
        commonPage.quit();
    }

    @Override
    public PageActions fullScreen() {
        commonPage.fullScreen();
        return this;
    }

    @Override
    public PageActions maximise() {
        commonPage.maximise();
        return this;
    }

    // ========================
    // Page Information
    // ========================

    @Override
    public String getUrl() {
        return commonPage.getUrl();
    }

    @Override
    public String getTitle() {
        return commonPage.getTitle();
    }

    // ========================
    // Element Targeting
    // ========================

    @Override
    public PageActions focus(Locator locator) {
        commonPage.focus(toTarget(locator));
        return this;
    }

    @Override
    public PageActions focus(Locator locator, int waitTimeMs) {
        commonPage.focus(waitTimeMs);
        commonPage.focus(toTarget(locator));
        return this;
    }

    @Override
    public boolean peek(Locator locator) {
        return commonPage.peek(toTarget(locator));
    }

    @Override
    public boolean peek(Locator locator, int waitTimeMs) {
        return commonPage.peek(toTarget(locator), waitTimeMs);
    }

    // ========================
    // Element Interaction
    // ========================

    @Override
    public PageActions click() {
        commonPage.click();
        return this;
    }

    @Override
    public PageActions dblClick() {
        commonPage.dblClick();
        return this;
    }

    @Override
    public PageActions type(String text) {
        // Use JavaScript to type text into the active element since 
        // Keyboard singleton has separate currentElement state
        commonPage.evaluate("document.activeElement.value += '" + text.replace("'", "\\'").replace("\n", "\\n") + "'");
        // Trigger input event
        commonPage.evaluate("document.activeElement.dispatchEvent(new Event('input', { bubbles: true }))");
        return this;
    }

    @Override
    public PageActions fill(String text) {
        // Clear and fill using JavaScript
        commonPage.evaluate("document.activeElement.value = '" + text.replace("'", "\\'").replace("\n", "\\n") + "'");
        // Trigger input and change events
        commonPage.evaluate("document.activeElement.dispatchEvent(new Event('input', { bubbles: true }))");
        commonPage.evaluate("document.activeElement.dispatchEvent(new Event('change', { bubbles: true }))");
        return this;
    }

    @Override
    public PageActions clear() {
        commonPage.clear();
        return this;
    }

    @Override
    public PageActions hover() {
        commonPage.hover();
        return this;
    }

    @Override
    public PageActions hover(Locator locator) {
        commonPage.hover(toTarget(locator));
        return this;
    }

    @Override
    public PageActions dragAndDrop(Locator source, Locator target) {
        commonPage.dragDrop(toTarget(source), toTarget(target));
        return this;
    }

    @Override
    public PageActions uploadFile(String filePath) {
        commonPage.file(filePath);
        return this;
    }

    // ========================
    // Element Information
    // ========================

    @Override
    public String getText() {
        return commonPage.get(Enums.ElementTrait.TEXT);
    }

    @Override
    public String get(FrameworkEnums.ElementTrait trait) {
        return commonPage.get(mapTrait(trait));
    }

    @Override
    public String getAttribute(String attributeName) {
        // Use JavaScript to get custom attribute
        return commonPage.javascript("return arguments[0].getAttribute('" + attributeName + "')");
    }

    // ========================
    // Collections
    // ========================

    @Override
    public PageActions collect(Locator locator) {
        commonPage.collect(toTarget(locator));
        return this;
    }

    @Override
    public int size() {
        return commonPage.size();
    }

    @Override
    public PageActions choose(int index) {
        commonPage.choose(index);
        return this;
    }

    @Override
    public PageActions choose(String text) {
        commonPage.choose(text);
        return this;
    }

    @Override
    public PageActions choose(FrameworkEnums.Index index) {
        switch (index) {
            case FIRST: commonPage.choose(Enums.Index.FIRST); break;
            case LAST: commonPage.choose(commonPage.size() - 1); break;
            case RANDOM: commonPage.choose((int)(Math.random() * commonPage.size())); break;
        }
        return this;
    }

    @Override
    public List<String> getCollectionTexts() {
        // This would need implementation based on CommonPage's collection handling
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // ========================
    // Assertions
    // ========================

    @Override
    public PageActions matches(String expectedText) {
        commonPage.matches(expectedText);
        return this;
    }

    @Override
    public PageActions contains(String partialText) {
        commonPage.contains(partialText);
        return this;
    }

    @Override
    public PageActions assertState(FrameworkEnums.ElementState state) {
        switch (state) {
            case VISIBLE: commonPage.visible(); break;
            case HIDDEN: commonPage.hidden(); break;
            case ENABLED: commonPage.enabled(); break;
            case DISABLED: commonPage.disabled(); break;
            case SELECTED: commonPage.selected(); break;
            case CLICKABLE: commonPage.clickable(); break;
            default: break;
        }
        return this;
    }

    @Override
    public PageActions present(String text) {
        commonPage.present(text);
        return this;
    }

    @Override
    public PageActions absent(String text) {
        commonPage.absent(text);
        return this;
    }

    // ========================
    // Waits
    // ========================

    @Override
    public PageActions waitFor(Locator locator, FrameworkEnums.ElementState state) {
        Enums.ElementState mappedState = mapElementState(state);
        commonPage.probe(toTarget(locator), mappedState);
        return this;
    }

    @Override
    public PageActions waitFor(Locator locator, FrameworkEnums.ElementState state, int waitTimeMs) {
        commonPage.probe(waitTimeMs);
        Enums.ElementState mappedState = mapElementState(state);
        commonPage.probe(toTarget(locator), mappedState);
        return this;
    }

    private Enums.ElementState mapElementState(FrameworkEnums.ElementState state) {
        switch (state) {
            case VISIBLE: return Enums.ElementState.VISIBILITY;
            case HIDDEN: return Enums.ElementState.INVISIBILITY;
            case ENABLED: return Enums.ElementState.ENABLED;
            case DISABLED: return Enums.ElementState.DISABLED;
            case PRESENT: return Enums.ElementState.PRESENCE;
            case ABSENT: return Enums.ElementState.ABSENCE;
            case EDITABLE: return Enums.ElementState.EDITABLE;
            default: return Enums.ElementState.VISIBILITY;
        }
    }

    @Override
    public PageActions pause(long milliseconds) {
        commonPage.pause(milliseconds);
        return this;
    }

    // ========================
    // Frames
    // ========================

    @Override
    public PageActions frame(String nameOrId) {
        commonPage.frame(nameOrId);
        return this;
    }

    @Override
    public PageActions frame(int index) {
        commonPage.frame(index);
        return this;
    }

    @Override
    public PageActions frame(Locator locator) {
        commonPage.frame(toTarget(locator));
        return this;
    }

    @Override
    public PageActions defaultContent() {
        commonPage.frame();
        return this;
    }

    // ========================
    // Windows/Tabs
    // ========================

    @Override
    public PageActions window(FrameworkEnums.Window window) {
        switch (window) {
            case MAIN: commonPage.window(Enums.Window.HOME); break;
            case NEW: commonPage.window(1); break;  // Switch to second window
            case PREVIOUS: commonPage.window(0); break;  // Switch back to first window
            case NEXT: commonPage.window(1); break;
        }
        return this;
    }

    @Override
    public PageActions window(int index) {
        commonPage.window(index);
        return this;
    }

    // ========================
    // JavaScript
    // ========================

    @Override
    public Object executeScript(String script) {
        return commonPage.evaluate(script);
    }

    @Override
    public Object executeScript(String script, Object... args) {
        // Playwright handles this differently; simplified implementation
        return commonPage.evaluate(script);
    }

    // ========================
    // Screenshots
    // ========================

    @Override
    public PageActions screenshot(String filename) {
        commonPage.screenshot(filename);
        return this;
    }

    @Override
    public void screenshotAndFail(String message) {
        commonPage.takeScreenShotAndExit(message);
    }

    // ========================
    // Alerts
    // ========================

    @Override
    public PageActions alert(FrameworkEnums.AlertAction action) {
        // Playwright handles dialogs differently via page.onDialog()
        // This would need specific implementation
        throw new UnsupportedOperationException("Use page.onDialog() for Playwright");
    }

    @Override
    public String getAlertText() {
        throw new UnsupportedOperationException("Use page.onDialog() for Playwright");
    }

    @Override
    public PageActions alertPrompt(String text, FrameworkEnums.AlertAction action) {
        throw new UnsupportedOperationException("Use page.onDialog() for Playwright");
    }

    // ========================
    // Logging
    // ========================

    @Override
    public PageActions log(String... messages) {
        commonPage.log(messages);
        return this;
    }

    // ========================
    // Storage Provider
    // ========================

    @Override
    public void store(String key, String value) {
        commonPage.store(key, value);
    }

    @Override
    public String retrieve(String key) {
        return commonPage.retrieve(key);
    }
}
