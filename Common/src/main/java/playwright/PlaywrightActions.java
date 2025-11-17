package playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import core.ui.Target;
import core.ui.TargetFactory;
import core.ui.UiActions;
import java.util.Collections;
import java.util.List;

public class PlaywrightActions implements UiActions {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private Target currentTarget;
    private List<Locator> collected = Collections.emptyList();
    private int chosenIndex = -1;

    public PlaywrightActions() {
        this(true);
    }

    public PlaywrightActions(boolean headless) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        page = browser.newPage();
    }

    @Override
    public void open(String url) {
        page.navigate(url);
    }

    @Override
    public void focus(Target target) {
        page.locator(toSelector(target)).focus();
        this.currentTarget = target;
        // Reset any previously chosen element when focus changes
        this.chosenIndex = -1;
    }

    // Maintain element context only via focus(selector)

    @Override
    public void click(Target target) {
        focus(target);
        click();
    }

    @Override
    public void click() {
        currentLocator().click();
    }

    @Override
    public void compose(Target target, String text) {
        focus(target);
        compose(text);
    }

    @Override
    public void compose(String text) {
        currentLocator().fill(text);
    }

    @Override
    public String getText(Target target) {
        focus(target);
        return getText();
    }

    @Override
    public String getText() {
        return currentLocator().textContent();
    }

    @Override
    public void close() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Override
    public boolean exists(Target target) {
        focus(target);
        return exists();
    }

    @Override
    public boolean exists() {
        return currentLocator().count() > 0;
    }

    @Override
    public boolean isVisible(Target target) {
        focus(target);
        return isVisible();
    }

    @Override
    public boolean isVisible() {
        return currentLocator().isVisible();
    }

    @Override
    public void waitForVisible(Target target, long timeoutMs) {
        focus(target);
        waitForVisible(timeoutMs);
    }

    @Override
    public void waitForVisible(long timeoutMs) {
        currentLocator().waitFor(new Locator.WaitForOptions()
                .setTimeout(timeoutMs)
                .setState(WaitForSelectorState.VISIBLE));
    }

    @Override
    public String value(Target target) {
        focus(target);
        return value();
    }

    @Override
    public String value() {
        Locator loc = currentLocator();
        try {
            return loc.inputValue();
        } catch (RuntimeException e) {
            String txt = loc.textContent();
            return txt == null ? "" : txt;
        }
    }

    @Override
    public String attribute(Target target, String name) {
        focus(target);
        return attribute(name);
    }

    @Override
    public String attribute(String name) {
        return currentLocator().getAttribute(name);
    }

    @Override
    public void hover(Target target) {
        focus(target);
        hover();
    }

    @Override
    public void hover() {
        currentLocator().hover();
    }

    @Override
    public void back() {
        page.goBack();
    }

    @Override
    public String title() {
        return page.title();
    }

    @Override
    public String url() {
        return page.url();
    }

    @Override
    public void screenshot(String path) {
        page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get(path)));
    }

    // --- Extended convenience methods (additive API) ---

    /** Reload the current page. */
    @Override
    public void refresh() {
        page.reload();
    }

    /** Navigate forward in history, if possible. */
    @Override
    public void forward() {
        page.goForward();
    }

    /** Clear the value of an input-like element. */
    @Override
    public void clear(Target target) {
        focus(target);
        clear();
    }

    @Override
    public void clear() {
        currentLocator().fill("");
    }

    /** Double-click the element. */
    @Override
    public void doubleClick(Target target) {
        focus(target);
        doubleClick();
    }

    @Override
    public void doubleClick() {
        currentLocator().dblclick();
    }

    /** Select an option from a select element by visible text (label). */
    @Override
    public void selectByText(Target target, String text) {
        focus(target);
        selectByText(text);
    }

    @Override
    public void selectByText(String text) {
        currentLocator().selectOption(new SelectOption().setLabel(text));
    }

    /** Select an option from a select element by value. */
    @Override
    public void selectByValue(Target target, String value) {
        focus(target);
        selectByValue(value);
    }

    @Override
    public void selectByValue(String value) {
        currentLocator().selectOption(new SelectOption().setValue(value));
    }

    /** Wait until the element is hidden or detached. */
    @Override
    public void waitForHidden(Target target, long timeoutMs) {
        focus(target);
        waitForHidden(timeoutMs);
    }

    @Override
    public void waitForHidden(long timeoutMs) {
        currentLocator().waitFor(new Locator.WaitForOptions()
                .setTimeout(timeoutMs)
                .setState(WaitForSelectorState.HIDDEN));
    }

    /** Scroll the element into view if needed. */
    @Override
    public void scrollIntoView(Target target) {
        focus(target);
        scrollIntoView();
    }

    @Override
    public void scrollIntoView() {
        currentLocator().scrollIntoViewIfNeeded();
    }

    /** Press a key or key combination on the element (e.g., "Control+A"). */
    @Override
    public void press(Target target, String key) {
        focus(target);
        press(key);
    }

    @Override
    public void press(String key) {
        currentLocator().press(key);
    }

    /** Press one or more key sequences on the element. */
    @Override
    public void press(Target target, CharSequence... keys) {
        focus(target);
        press(keys);
    }

    @Override
    public void press(CharSequence... keys) {
        if (keys == null || keys.length == 0) return;
        for (CharSequence k : keys) {
            if (k != null) {
                currentLocator().press(k.toString());
            }
        }
    }

    /** Set the checked state of a checkbox-like element. */
    @Override
    public void setChecked(Target target, boolean checked) {
        focus(target);
        setChecked(checked);
    }

    @Override
    public void setChecked(boolean checked) {
        currentLocator().setChecked(checked);
    }

    /** Upload a file to an input[type=file]. */
    @Override
    public void uploadFile(Target target, String path) {
        focus(target);
        uploadFile(path);
    }

    @Override
    public void uploadFile(String path) {
        currentLocator().setInputFiles(java.nio.file.Paths.get(path));
    }

    // ---- Collection utilities ----
    @Override
    public void collect(Target target) {
        String selector = toSelector(target);
        this.collected = page.locator(selector).all();
        this.chosenIndex = -1;
        // keep knowledge of the target for consistency
        // but currentTarget is not changed by collect
    }

    @Override
    public int size() {
        return collected == null ? 0 : collected.size();
    }

    @Override
    public void choose(int index) {
        if (collected == null || collected.isEmpty()) {
            throw new IllegalStateException("No elements have been collected. Call collect(target) first.");
        }
        if (index < 0 || index >= collected.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for collected size " + collected.size());
        }
        this.chosenIndex = index;
    }

    private String requireContext() {
        if (currentTarget == null) {
            throw new IllegalStateException("No element context set. Call focus(target) first.");
        }
        return toSelector(currentTarget);
    }

    private Locator currentLocator() {
        if (chosenIndex >= 0 && collected != null && chosenIndex < collected.size()) {
            return collected.get(chosenIndex);
        }
        return page.locator(requireContext());
    }

    private String toSelector(Target target) {
        return switch (target.strategy()) {
            case CSS -> target.value();
            case XPATH -> "xpath=" + target.value();
            case ID -> "#" + target.value();
            case NAME -> "[name=\"" + escapeCssDoubleQuoted(target.value()) + "\"]";
            case CLASS_NAME -> "." + target.value();
            case TAG_NAME -> target.value();
            case LINK_TEXT -> "xpath=//a[normalize-space(text())=" + escapeForXPath(target.value()) + "]";
            case PARTIAL_LINK_TEXT -> "xpath=//a[contains(normalize-space(text()), " + escapeForXPath(target.value()) + ")]";
            case TEXT -> "text=" + target.value();
            case DATA_TEST_ID -> "[data-testid=\"" + escapeCssDoubleQuoted(target.value()) + "\"]";
            case ROLE -> "role=" + target.value();
        };
    }

    private String escapeCssDoubleQuoted(String s) {
        // Escape backslash and double quotes for CSS double-quoted attribute selectors
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String escapeForXPath(String s) {
        // Return a valid XPath string literal representing s.
        // If s contains no single quote, wrap with single quotes.
        if (s.indexOf('\'') < 0) {
            return "'" + s + "'";
        }
        // Otherwise, build concat('part1', "'", 'part2', ...)
        String[] parts = s.split("'", -1); // keep empty parts
        StringBuilder sb = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append("'").append(parts[i]).append("'");
            if (i < parts.length - 1) {
                sb.append(", \"'\"");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
