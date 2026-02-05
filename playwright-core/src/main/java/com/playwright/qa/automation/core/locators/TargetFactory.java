package com.playwright.qa.automation.core.locators;

import com.playwright.qa.automation.core.Enums;

import java.util.StringTokenizer;

import static com.playwright.qa.automation.core.locators.Target.Type.*;

/**
 * Factory class for creating Target locators using Playwright-compatible selectors.
 * Provides utility methods for focus(), descend(), expect(), depart(), collect(), choose() and contains() methods.
 * 
 * Playwright supports several selector engines:
 * - CSS selectors: "css=button" or just "button"
 * - XPath selectors: "xpath=//button"
 * - Text selectors: "text=Submit" or "text=/Submit/i" (regex)
 * - Accessibility selectors: "role=button[name='Submit']"
 * - Test ID selectors: "[data-testid='submit']"
 */
public class TargetFactory {

    public TargetFactory() {
    }

    /////////////
    // TARGETS //
    /////////////

    /**
     * Locates an element with the given attribute and value.
     * <br><br>
     * <b style="color:blue;">attribute("data-testid", "data-testid-example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;span data-testid="data-testid-example"&gt;&lt;/span&gt;
     * </div>
     */
    protected static Target attribute(String attribute, String value) {
        return new Target(String.format("[%s=\"%s\"]", attribute, value), attribute, value, CSS);
    }

    /**
     * Locates an element with a given standard HTML5 attribute and value.
     * <br><br>
     * <b style="color:blue;">attribute(TYPE, "password")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input type="password"&gt;
     * </div>
     */
    protected static Target attribute(Enums.Attribute attribute, String value) {
        return new Target(String.format("[%s=\"%s\"]", attribute.getValue(), value), attribute.getValue(), value, CSS);
    }

    /**
     * Locates an element with the given attribute and partial value.
     * <br><br>
     * <b style="color:blue;">partialAttribute("data-testid", "example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;span data-testid="data-testid-example"&gt;&lt;/span&gt;
     * </div>
     */
    protected static Target partialAttribute(String attribute, String value) {
        return new Target(String.format("[%s*=\"%s\"]", attribute, value), attribute, value, CSS);
    }

    /**
     * Locates an element with a given standard HTML5 attribute and partial value.
     */
    protected static Target partialAttribute(Enums.Attribute attribute, String value) {
        return new Target(String.format("[%s*=\"%s\"]", attribute.getValue(), value), attribute.getValue(), value, CSS);
    }

    /**
     * Locates an element with the given attribute present (any value).
     * <br><br>
     * <b style="color:blue;">attribute("enabled")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div enabled&gt;&lt;/div&gt;
     * </div>
     */
    protected static Target attribute(String attribute) {
        return new Target(String.format("[%s]", attribute), attribute, ATTRIBUTE);
    }

    /**
     * Locates an element with the given class name.
     * <br><br>
     * <b style="color:blue;">className("abc")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input class="abc"&gt;
     * </div>
     */
    public static Target className(String className) {
        return new Target("." + className, className, CLASS);
    }

    /**
     * Locates an element with multiple class names.
     * <br><br>
     * <b style="color:blue;">classNames("abc def")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div class="abc def"&gt;&lt;/div&gt;
     * </div>
     */
    protected static Target classNames(String classNames) {
        StringTokenizer st = new StringTokenizer(classNames);
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            sb.append(".");
            sb.append(st.nextToken());
        }
        return new Target(sb.toString(), classNames, CLASSES);
    }

    /**
     * Locates an element with the given id.
     * <br><br>
     * <b style="color:blue;">id("test")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div id="test"&gt;&lt;/div&gt;
     * </div>
     */
    public static Target id(String value) {
        return new Target("#" + value, value, ID);
    }

    /**
     * Locates an element with the given name attribute.
     * <br><br>
     * <b style="color:blue;">name("test")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div name="test"&gt;&lt;/div&gt;
     * </div>
     */
    public static Target name(String value) {
        return new Target(String.format("[name=\"%s\"]", value), value, NAME);
    }

    /**
     * Locates an element with a CSS selector.
     */
    protected static Target css(String value) {
        return new Target(value, value, CSS);
    }

    /**
     * Locates an element with the given placeholder.
     * <br><br>
     * <b style="color:blue;">placeholder("please type here")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input placeholder="please type here"&gt;
     * </div>
     */
    protected static Target placeholder(String value) {
        return new Target(String.format("[placeholder=\"%s\"]", value), value, PLACEHOLDER);
    }

    /**
     * Locates an element with the given title.
     * <br><br>
     * <b style="color:blue;">title("this is title")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input title="this is title"&gt;
     * </div>
     */
    protected static Target title(String value) {
        return new Target(String.format("[title=\"%s\"]", value), value, TITLE);
    }

    /**
     * Locates an element with the given role attribute.
     * <br><br>
     * <b style="color:blue;">role("button")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div role="button"&gt;&lt;/div&gt;
     * </div>
     */
    protected static Target role(String value) {
        return new Target(String.format("[role=\"%s\"]", value), value, ROLE);
    }

    /**
     * Locates an element using Playwright's built-in role selector.
     * This uses Playwright's getByRole() internally.
     * <br><br>
     * <b style="color:blue;">byRole("button", "Submit")</b>
     */
    protected static Target byRole(String role, String name) {
        return new Target(String.format("role=%s[name=\"%s\"]", role, name), role, name, GET_BY_ROLE);
    }

    /**
     * Locates an element using Playwright's built-in role selector (without name).
     */
    protected static Target byRole(String role) {
        return new Target(String.format("role=%s", role), role, GET_BY_ROLE);
    }

    /**
     * Locates an element with the given data-testid.
     * <br><br>
     * <b style="color:blue;">dataTestId("sidemenu-button")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;button data-testid="sidemenu-button"&gt;&lt;/button&gt;
     * </div>
     */
    protected static Target dataTestId(String value) {
        return new Target(String.format("[data-testid=\"%s\"]", value), "data-testid", value, DATA_TEST_ID);
    }

    /**
     * Locates an element with the given href.
     * <br><br>
     * <b style="color:blue;">href("https://www.google.co.uk/")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;a href="https://www.google.co.uk/"&gt;&lt;/a&gt;
     * </div>
     */
    protected static Target href(String value) {
        return new Target(String.format("[href=\"%s\"]", value), value, HREF);
    }

    /**
     * Locates an element with the given src.
     * <br><br>
     * <b style="color:blue;">src("example.jpg")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;img src="example.jpg"&gt;
     * </div>
     */
    protected static Target src(String value) {
        return new Target(String.format("[src=\"%s\"]", value), value, SRC);
    }

    /**
     * Locates an element with the given style.
     */
    protected static Target style(String value) {
        return new Target(String.format("[style=\"%s\"]", value), value, STYLE);
    }

    /**
     * Locates an element with the given alt text.
     */
    protected static Target alt(String value) {
        return new Target(String.format("[alt=\"%s\"]", value), value, ALT);
    }

    /**
     * Locates an element with the given type attribute.
     */
    protected static Target type(String value) {
        return new Target(String.format("[type=\"%s\"]", value), value, TYPE);
    }

    /**
     * Locates an element with the given value attribute.
     */
    protected static Target value(String value) {
        return new Target(String.format("[value=\"%s\"]", value), value, VALUE);
    }

    /**
     * Locates an element by its exact text content.
     * <br><br>
     * <b style="color:blue;">text("Submit")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;button&gt;Submit&lt;/button&gt;
     * </div>
     */
    public static Target text(String value) {
        return new Target(String.format("text=\"%s\"", value), value, TEXT);
    }

    /**
     * Locates an element by partial text content.
     * <br><br>
     * <b style="color:blue;">partialText("Sub")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;button&gt;Submit&lt;/button&gt;
     * </div>
     */
    protected static Target partialText(String value) {
        return new Target(String.format("text=%s", value), value, PARTIAL_TEXT);
    }

    /**
     * Locates an element by tag with specific text content.
     */
    protected static Target tagWithText(Enums.Tag tag, String text) {
        String tagName = tag.name().toLowerCase();
        return new Target(String.format("%s:has-text(\"%s\")", tagName, text), text, TAG_WITH_TEXT);
    }

    /**
     * Locates an element by tag that contains specific text.
     */
    protected static Target tagContainsText(Enums.Tag tag, String text) {
        String tagName = tag.name().toLowerCase();
        return new Target(String.format("%s:has-text(\"%s\")", tagName, text), text, TAG_CONTAINS_TEXT);
    }

    /**
     * Locates an element by tag with specific id.
     */
    protected static Target tagWithId(Enums.Tag tag, String id) {
        String tagName = tag.name().toLowerCase();
        return new Target(String.format("%s#%s", tagName, id), id, TAG_WITH_ID);
    }

    /**
     * Locates an element by tag with specific class.
     */
    protected static Target tagWithClass(Enums.Tag tag, String className) {
        String tagName = tag.name().toLowerCase();
        return new Target(String.format("%s.%s", tagName, className), className, TAG_WITH_CLASS);
    }

    /**
     * Locates an element by tag with specific title.
     */
    protected static Target tagWithTitle(Enums.Tag tag, String title) {
        String tagName = tag.name().toLowerCase();
        return new Target(String.format("%s[title=\"%s\"]", tagName, title), title, TAG_WITH_TITLE);
    }

    /**
     * Locates an element by tag with specific name.
     */
    protected static Target tagWithName(Enums.Tag tag, String name) {
        String tagName = tag.name().toLowerCase();
        return new Target(String.format("%s[name=\"%s\"]", tagName, name), name, TAG_WITH_NAME);
    }

    /**
     * Locates an element by tag with specific value.
     */
    protected static Target tagWithValue(Enums.Tag tag, String value) {
        String tagName = tag.name().toLowerCase();
        return new Target(String.format("%s[value=\"%s\"]", tagName, value), value, TAG_WITH_VALUE);
    }

    /**
     * Locates an element by tag name.
     */
    public static Target tag(Enums.Tag tag) {
        String tagName = tag.name().toLowerCase();
        return new Target(tagName, tagName, TAG);
    }

    /**
     * Locates a link by its text.
     */
    protected static Target linkText(String linkText) {
        return new Target(String.format("a:has-text(\"%s\")", linkText), linkText, LINK_TEXT);
    }

    /**
     * Locates a link by partial text.
     */
    protected static Target partialLinkText(String partialLinkText) {
        return new Target(String.format("a:has-text(\"%s\")", partialLinkText), partialLinkText, PARTIAL_LINK_TEXT);
    }

    /**
     * Locates an element using an XPath expression.
     */
    public static Target xpath(String xpath) {
        return new Target("xpath=" + xpath, xpath, XPATH);
    }

    /**
     * Returns a target representing the document root.
     */
    protected static Target root() {
        return new Target("html", "html", ROOT);
    }

    /**
     * Returns a target for child elements selector.
     */
    protected static Target children() {
        return new Target(":first-child", ":first-child", CHILDREN);
    }

    /**
     * Returns a target for sibling elements selector.
     */
    protected static Target siblings() {
        return new Target("~ *", "~ *", SIBLINGS);
    }

    // ============================================
    // Playwright-specific locator methods
    // ============================================

    /**
     * Locates an element using Playwright's getByLabel locator.
     * This finds form controls by their associated label text.
     * <br><br>
     * <b style="color:blue;">byLabel("Username")</b>
     */
    protected static Target byLabel(String label) {
        return new Target(String.format("label:has-text(\"%s\") + input, label:has-text(\"%s\") + textarea", label, label), label, LABEL);
    }

    /**
     * Locates an element using Playwright's getByAltText locator.
     * This finds images by their alt text.
     */
    protected static Target byAltText(String altText) {
        return new Target(String.format("[alt=\"%s\"]", altText), altText, ALT_TEXT);
    }

    /**
     * Locates an element using Playwright's getByTestId locator.
     * By default, this uses data-testid attribute.
     */
    protected static Target byTestId(String testId) {
        return new Target(String.format("[data-testid=\"%s\"]", testId), testId, TEST_ID);
    }

    /**
     * Locates an element using nth-child selector.
     *
     * @param base  The base selector
     * @param index The 1-based index
     */
    protected static Target nth(String base, int index) {
        return new Target(String.format("%s >> nth=%d", base, index - 1), base + "[" + index + "]", CSS);
    }

    /**
     * Locates the first matching element.
     */
    protected static Target first(String base) {
        return new Target(String.format("%s >> nth=0", base), base + "[first]", CSS);
    }

    /**
     * Locates the last matching element.
     */
    protected static Target last(String base) {
        return new Target(String.format("%s >> nth=-1", base), base + "[last]", CSS);
    }
}

