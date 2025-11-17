package selenium;

import core.ui.Target;
import core.ui.TargetFactory;
import core.ui.UiActions;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class SeleniumActions implements UiActions {
    private final WebDriver driver;
    private Target currentTarget;
    private List<WebElement> collected = Collections.emptyList();
    private int chosenIndex = -1;

    // Suppress noisy Selenium CDP mismatch warnings in test console output.
    // Examples:
    //  - org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
    //    WARNING: Unable to find CDP implementation matching 142
    //  - org.openqa.selenium.chromium.ChromiumDriver lambda$new$5
    //    WARNING: Unable to find version of CDP to use for 142.x.x
    static { CdpWarningSilencer.silence(); }

    public SeleniumActions() {
        // Selenium Manager (since Selenium 4.6+) will resolve the driver automatically
        this.driver = new ChromeDriver();
    }

    /**
     * Create a Selenium-based UiActions with optional headless mode.
     * This constructor is additive and keeps the default behavior unchanged.
     */
    public SeleniumActions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            // Use new headless for modern Chrome
            options.addArguments("--headless=new");
        }

        // Improve stability in CI/containerized environments (e.g., GitHub Actions)
        // These flags are no-ops on local machines but prevent Chrome from crashing in sandboxed runners.
        // Apply when headless or when CI environment variable is present.
        boolean isCi = Boolean.parseBoolean(System.getenv().getOrDefault("CI", "false"));
        if (headless || isCi) {
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            // Avoid origin checks that can fail under some runner network setups
            options.addArguments("--remote-allow-origins=*");
            // Set a deterministic window size so some layouts don’t hide elements
            options.addArguments("--window-size=1920,1080");
        }
        this.driver = new ChromeDriver(options);
    }

    @Override
    public void open(String url) {
        driver.get(url);
    }

    @Override
    public void focus(Target target) {
        WebElement el = driver.findElement(toBy(target));
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript("arguments[0].focus();", el);
        } else {
            // fallback: click to focus
            el.click();
        }
        this.currentTarget = target;
        // reset any chosen element when focus changes
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
        currentElement().click();
    }

    @Override
    public void compose(Target target, String text) {
        focus(target);
        compose(text);
    }

    @Override
    public void compose(String text) {
        WebElement el = currentElement();
        el.clear();
        el.sendKeys(text);
    }

    @Override
    public String getText(Target target) {
        // Ensure the target is focused and visible before reading text to avoid stale elements
        focus(target);
        try {
            waitForVisible(target, 5000);
        } catch (Exception ignored) {
            // proceed even if the wait timed out; a retry in getText() below may still succeed
        }
        return getText();
    }

    @Override
    public String getText() {
        // Retry a few times to mitigate StaleElementReferenceException that can occur after navigation
        int attempts = 0;
        while (true) {
            try {
                // Wait briefly for the element to be present and visible each attempt
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement el;
                if (chosenIndex >= 0 && collected != null && chosenIndex < collected.size()) {
                    el = collected.get(chosenIndex);
                    wait.until(ExpectedConditions.visibilityOf(el));
                } else {
                    By by = toBy(requireContext());
                    el = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                }
                return el.getText();
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                if (++attempts >= 3) throw e;
                // small backoff before retrying
                try { Thread.sleep(150L * attempts); } catch (InterruptedException ignored) { }
            }
        }
    }

    @Override
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public boolean exists(Target target) {
        focus(target);
        return exists();
    }

    @Override
    public boolean exists() {
        if (chosenIndex >= 0 && collected != null && chosenIndex < collected.size()) {
            try {
                return collected.get(chosenIndex).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }
        return !driver.findElements(toBy(requireContext())).isEmpty();
    }

    @Override
    public boolean isVisible(Target target) {
        focus(target);
        return isVisible();
    }

    @Override
    public boolean isVisible() {
        try {
            if (chosenIndex >= 0 && collected != null && chosenIndex < collected.size()) {
                return collected.get(chosenIndex).isDisplayed();
            }
            return driver.findElement(toBy(requireContext())).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void waitForVisible(Target target, long timeoutMs) {
        focus(target);
        waitForVisible(timeoutMs);
    }

    @Override
    public void waitForVisible(long timeoutMs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMs));
        if (chosenIndex >= 0 && collected != null && chosenIndex < collected.size()) {
            wait.until(ExpectedConditions.visibilityOf(collected.get(chosenIndex)));
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(toBy(requireContext())));
        }
    }

    @Override
    public String value(Target target) {
        focus(target);
        return value();
    }

    @Override
    public String value() {
        WebElement el = currentElement();
        String val = el.getAttribute("value");
        if (val != null) return val;
        String txt = el.getText();
        return txt == null ? "" : txt;
    }

    @Override
    public String attribute(Target target, String name) {
        focus(target);
        return attribute(name);
    }

    @Override
    public String attribute(String name) {
        return currentElement().getAttribute(name);
    }

    @Override
    public void hover(Target target) {
        focus(target);
        hover();
    }

    @Override
    public void hover() {
        WebElement el = currentElement();
        new Actions(driver).moveToElement(el).perform();
    }

    @Override
    public void back() {
        driver.navigate().back();
    }

    @Override
    public String title() {
        return driver.getTitle();
    }

    @Override
    public String url() {
        return driver.getCurrentUrl();
    }

    @Override
    public void screenshot(String path) {
        if (!(driver instanceof TakesScreenshot ts)) {
            throw new IllegalStateException("Driver does not support screenshots");
        }
        byte[] png = ts.getScreenshotAs(OutputType.BYTES);
        try {
            Path p = Path.of(path);
            Files.createDirectories(p.getParent() == null ? Path.of(".") : p.getParent());
            Files.write(p, png);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write screenshot to " + path, e);
        }
    }

    // --- Extended convenience methods (additive API) ---

    /** Reload the current page. */
    @Override
    public void refresh() {
        driver.navigate().refresh();
    }

    /** Navigate forward in history. */
    @Override
    public void forward() {
        driver.navigate().forward();
    }

    /** Clear the value of an input-like element. */
    @Override
    public void clear(Target target) {
        focus(target);
        clear();
    }

    @Override
    public void clear() {
        currentElement().clear();
    }

    /** Double-click the element. */
    @Override
    public void doubleClick(Target target) {
        focus(target);
        doubleClick();
    }

    @Override
    public void doubleClick() {
        WebElement el = currentElement();
        new Actions(driver).doubleClick(el).perform();
    }

    /** Select option by visible text. */
    @Override
    public void selectByText(Target target, String text) {
        focus(target);
        selectByText(text);
    }

    @Override
    public void selectByText(String text) {
        new Select(currentElement()).selectByVisibleText(text);
    }

    /** Select option by value attribute. */
    @Override
    public void selectByValue(Target target, String value) {
        focus(target);
        selectByValue(value);
    }

    @Override
    public void selectByValue(String value) {
        new Select(currentElement()).selectByValue(value);
    }

    /** Wait until the element becomes hidden or not present. */
    @Override
    public void waitForHidden(Target target, long timeoutMs) {
        focus(target);
        waitForHidden(timeoutMs);
    }

    @Override
    public void waitForHidden(long timeoutMs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMs));
        if (chosenIndex >= 0 && collected != null && chosenIndex < collected.size()) {
            wait.until(ExpectedConditions.invisibilityOf(collected.get(chosenIndex)));
        } else {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(toBy(requireContext())));
        }
    }

    /** Scroll element into view using JavaScript. */
    @Override
    public void scrollIntoView(Target target) {
        focus(target);
        scrollIntoView();
    }

    @Override
    public void scrollIntoView() {
        WebElement el = currentElement();
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", el);
        } else {
            new Actions(driver).moveToElement(el).perform();
        }
    }

    /** Send a key or chord to the element (e.g., Keys.CONTROL+"a" via chord in caller). */
    @Override
    public void press(Target target, CharSequence... keys) {
        focus(target);
        press(keys);
    }

    /** Send a single key to the element by its name (e.g., "Enter"). */
    @Override
    public void press(Target target, String key) {
        focus(target);
        press(key);
    }

    @Override
    public void press(String key) {
        CharSequence mapped = mapToKeys(key);
        currentElement().sendKeys(mapped);
    }

    @Override
    public void press(CharSequence... keys) {
        currentElement().sendKeys(keys);
    }

    private CharSequence mapToKeys(String key) {
        if (key == null) return "";
        String k = key.trim();
        // Common aliases mapping
        switch (k) {
            case "Enter": return Keys.ENTER;
            case "Tab": return Keys.TAB;
            case "Escape": return Keys.ESCAPE;
            case "Backspace": return Keys.BACK_SPACE;
            case "Delete": return Keys.DELETE;
            case "ArrowDown": return Keys.ARROW_DOWN;
            case "ArrowUp": return Keys.ARROW_UP;
            case "ArrowLeft": return Keys.ARROW_LEFT;
            case "ArrowRight": return Keys.ARROW_RIGHT;
            case "Home": return Keys.HOME;
            case "End": return Keys.END;
            case "PageUp": return Keys.PAGE_UP;
            case "PageDown": return Keys.PAGE_DOWN;
        }
        // Fallback: try enum by normalized form
        String norm = k.toUpperCase().replace(' ', '_').replace('-', '_');
        try {
            return Keys.valueOf(norm);
        } catch (IllegalArgumentException ex) {
            // Not a known special key, return the literal string
            return key;
        }
    }

    /** Set checkbox/radio checked state. */
    @Override
    public void setChecked(Target target, boolean checked) {
        focus(target);
        setChecked(checked);
    }

    @Override
    public void setChecked(boolean checked) {
        WebElement el = currentElement();
        if (el.isSelected() != checked) {
            el.click();
        }
    }

    /** Upload a file to input[type=file]. */
    @Override
    public void uploadFile(Target target, String path) {
        focus(target);
        uploadFile(path);
    }

    @Override
    public void uploadFile(String path) {
        currentElement().sendKeys(path);
    }

    // ---- Collection utilities ----
    @Override
    public void collect(Target target) {
        this.collected = driver.findElements(toBy(target));
        this.chosenIndex = -1;
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

    private Target requireContext() {
        if (currentTarget == null) {
            throw new IllegalStateException("No element context set. Call focus(target) first.");
        }
        return currentTarget;
    }

    private WebElement currentElement() {
        if (chosenIndex >= 0 && collected != null && chosenIndex < collected.size()) {
            return collected.get(chosenIndex);
        }
        return driver.findElement(toBy(requireContext()));
    }

    private By toBy(Target target) {
        return switch (target.strategy()) {
            case CSS -> By.cssSelector(target.value());
            case XPATH -> By.xpath(target.value());
            case ID -> By.id(target.value());
            case NAME -> By.name(target.value());
            case CLASS_NAME -> By.className(target.value());
            case TAG_NAME -> By.tagName(target.value());
            case LINK_TEXT -> By.linkText(target.value());
            case PARTIAL_LINK_TEXT -> By.partialLinkText(target.value());
            case TEXT -> By.xpath("//*[normalize-space(text())='" + escapeQuotes(target.value()) + "']");
            case DATA_TEST_ID -> By.cssSelector("[data-testid='" + cssEscape(target.value()) + "']");
            case ROLE -> By.cssSelector("[role='" + cssEscape(target.value()) + "']");
        };
    }

    private String cssEscape(String s) { return s.replace("'", "\\'"); }
    private String escapeQuotes(String s) { return s.replace("'", "\"'\""); }
}
