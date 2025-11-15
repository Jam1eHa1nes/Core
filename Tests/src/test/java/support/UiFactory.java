package support;

import core.ui.UiActions;
import playwright.PlaywrightActions;
import selenium.SeleniumActions;

public class UiFactory {
    public static UiActions create() {
        String engine = System.getProperty("engine", System.getenv().getOrDefault("ENGINE", "playwright"));
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        if ("selenium".equalsIgnoreCase(engine)) {
            return new SeleniumActions();
        }
        // default: Playwright
        return new PlaywrightActions(headless);
    }
}
