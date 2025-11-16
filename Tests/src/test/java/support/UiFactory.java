package support;

import core.ui.UiActions;
import playwright.PlaywrightActions;
import selenium.SeleniumActions;
import selenium.CdpWarningSilencer;

public class UiFactory {
    public static UiActions create() {
        // Ensure Selenium CDP warning silencing is applied as early as possible
        CdpWarningSilencer.silence();
        String engine = System.getProperty("engine", System.getenv().getOrDefault("ENGINE", "playwright"));
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        if ("selenium".equalsIgnoreCase(engine)) {
            // Honor headless flag for Selenium as well
            return new SeleniumActions(headless);
        }
        // default: Playwright
        return new PlaywrightActions(headless);
    }
}
