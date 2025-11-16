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

public class PlaywrightActions implements UiActions {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private Target currentTarget;

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
    }

    // Maintain element context only via focus(selector)

    @Override
    public void click(Target target) {
        focus(target);
        click();
    }

    @Override
    public void click() {
        page.locator(requireContext()).click();
    }

    @Override
    public void compose(Target target, String text) {
        focus(target);
        compose(text);
    }

    @Override
    public void compose(String text) {
        page.locator(requireContext()).fill(text);
    }

    @Override
    public String getText(Target target) {
        focus(target);
        return getText();
    }

    @Override
    public String getText() {
        return page.locator(requireContext()).textContent();
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
        return page.locator(requireContext()).count() > 0;
    }

    @Override
    public boolean isVisible(Target target) {
        focus(target);
        return isVisible();
    }

    @Override
    public boolean isVisible() {
        return page.locator(requireContext()).isVisible();
    }

    @Override
    public void waitForVisible(Target target, long timeoutMs) {
        focus(target);
        waitForVisible(timeoutMs);
    }

    @Override
    public void waitForVisible(long timeoutMs) {
        page.locator(requireContext()).waitFor(new Locator.WaitForOptions()
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
        Locator loc = page.locator(requireContext());
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
        return page.locator(requireContext()).getAttribute(name);
    }

    @Override
    public void hover(Target target) {
        focus(target);
        hover();
    }

    @Override
    public void hover() {
        page.locator(requireContext()).hover();
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
        page.locator(requireContext()).fill("");
    }

    /** Double-click the element. */
    @Override
    public void doubleClick(Target target) {
        focus(target);
        doubleClick();
    }

    @Override
    public void doubleClick() {
        page.locator(requireContext()).dblclick();
    }

    /** Select an option from a select element by visible text (label). */
    @Override
    public void selectByText(Target target, String text) {
        focus(target);
        selectByText(text);
    }

    @Override
    public void selectByText(String text) {
        page.locator(requireContext()).selectOption(new SelectOption().setLabel(text));
    }

    /** Select an option from a select element by value. */
    @Override
    public void selectByValue(Target target, String value) {
        focus(target);
        selectByValue(value);
    }

    @Override
    public void selectByValue(String value) {
        page.locator(requireContext()).selectOption(new SelectOption().setValue(value));
    }

    /** Wait until the element is hidden or detached. */
    @Override
    public void waitForHidden(Target target, long timeoutMs) {
        focus(target);
        waitForHidden(timeoutMs);
    }

    @Override
    public void waitForHidden(long timeoutMs) {
        page.locator(requireContext()).waitFor(new Locator.WaitForOptions()
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
        page.locator(requireContext()).scrollIntoViewIfNeeded();
    }

    /** Press a key or key combination on the element (e.g., "Control+A"). */
    @Override
    public void press(Target target, String key) {
        focus(target);
        press(key);
    }

    @Override
    public void press(String key) {
        page.locator(requireContext()).press(key);
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
                page.locator(requireContext()).press(k.toString());
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
        page.locator(requireContext()).setChecked(checked);
    }

    /** Upload a file to an input[type=file]. */
    @Override
    public void uploadFile(Target target, String path) {
        focus(target);
        uploadFile(path);
    }

    @Override
    public void uploadFile(String path) {
        page.locator(requireContext()).setInputFiles(java.nio.file.Paths.get(path));
    }

    private String requireContext() {
        if (currentTarget == null) {
            throw new IllegalStateException("No element context set. Call focus(target) first.");
        }
        return toSelector(currentTarget);
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
