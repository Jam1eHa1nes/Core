package playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;
import core.ui.UiActions;

public class PlaywrightActions implements UiActions {
    private Playwright playwright;
    private Browser browser;
    private Page page;

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
    public void click(String selector) {
        page.locator(selector).click();
    }

    @Override
    public void compose(String selector, String text) {
        page.locator(selector).fill(text);
    }

    @Override
    public void focus(String selector) {
        page.locator(selector).focus();
    }

    @Override
    public String getText(String selector) {
        return page.locator(selector).textContent();
    }

    @Override
    public void close() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Override
    public boolean exists(String selector) {
        return page.locator(selector).count() > 0;
    }

    @Override
    public boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    @Override
    public void waitForVisible(String selector, long timeoutMs) {
        page.locator(selector).waitFor(new Locator.WaitForOptions()
                .setTimeout(timeoutMs)
                .setState(WaitForSelectorState.VISIBLE));
    }

    @Override
    public String value(String selector) {
        Locator loc = page.locator(selector);
        try {
            return loc.inputValue();
        } catch (RuntimeException e) {
            // Not an input-like element; fall back to text
            String txt = loc.textContent();
            return txt == null ? "" : txt;
        }
    }

    @Override
    public String attribute(String selector, String name) {
        return page.locator(selector).getAttribute(name);
    }

    @Override
    public void hover(String selector) {
        page.locator(selector).hover();
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
}
