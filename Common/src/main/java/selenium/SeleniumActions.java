package selenium;

import core.ui.UiActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class SeleniumActions implements UiActions {
    private final WebDriver driver;

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
        this.driver = new ChromeDriver(options);
    }

    @Override
    public void open(String url) {
        driver.get(url);
    }

    private WebElement find(String selector) {
        return driver.findElement(By.cssSelector(selector));
    }

    @Override
    public void click(String selector) {
        find(selector).click();
    }

    @Override
    public void compose(String selector, String text) {
        WebElement el = find(selector);
        el.clear();
        el.sendKeys(text);
    }

    @Override
    public void focus(String selector) {
        WebElement el = find(selector);
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript("arguments[0].focus();", el);
        } else {
            // fallback: click to focus
            el.click();
        }
    }

    @Override
    public String getText(String selector) {
        return find(selector).getText();
    }

    @Override
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public boolean exists(String selector) {
        return !driver.findElements(By.cssSelector(selector)).isEmpty();
    }

    @Override
    public boolean isVisible(String selector) {
        try {
            return find(selector).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void waitForVisible(String selector, long timeoutMs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeoutMs));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    @Override
    public String value(String selector) {
        WebElement el = find(selector);
        String val = el.getAttribute("value");
        if (val != null) return val;
        String txt = el.getText();
        return txt == null ? "" : txt;
    }

    @Override
    public String attribute(String selector, String name) {
        return find(selector).getAttribute(name);
    }

    @Override
    public void hover(String selector) {
        WebElement el = find(selector);
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
}
