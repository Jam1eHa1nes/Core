package com.core.qa.automation.tests.example;

import com.core.qa.automation.common.FrameworkEnums;
import com.core.qa.automation.common.PageActions;
import com.core.qa.automation.common.PageFactory;
import com.core.qa.automation.common.locators.Locator;
import com.core.qa.automation.common.locators.LocatorFactory;

/**
 * Example test showing how to write framework-agnostic tests.
 * <p>
 * This test will work with both Playwright and Selenium implementations
 * depending on which dependency is on the classpath.
 * <p>
 * <b>To switch frameworks:</b>
 * <ul>
 *   <li>Include playwright-core in your pom.xml for Playwright</li>
 *   <li>Include selenium-core in your pom.xml for Selenium</li>
 *   <li>Or set system property: -Dautomation.framework=playwright</li>
 *   <li>Or set environment variable: AUTOMATION_FRAMEWORK=selenium</li>
 * </ul>
 */
public class FrameworkAgnosticTestExample {

    // Define locators using the common LocatorFactory
    private static final Locator USERNAME_INPUT = LocatorFactory.id("username");
    private static final Locator PASSWORD_INPUT = LocatorFactory.id("password");
    private static final Locator LOGIN_BUTTON = LocatorFactory.css("button[type='submit']");
    private static final Locator WELCOME_MESSAGE = LocatorFactory.text("Welcome");
    private static final Locator SEARCH_INPUT = LocatorFactory.placeholder("Search...");
    private static final Locator MENU_ITEMS = LocatorFactory.css(".menu-item");
    
    public static void main(String[] args) {
        // Create PageActions - automatically uses whichever framework is available
        PageActions page = PageFactory.create();
        
        try {
            // Open browser and navigate
            page.open(FrameworkEnums.Browser.CHROME)
                .go("https://example.com/login")
                .maximise();
            
            // Login flow
            page.focus(USERNAME_INPUT)
                .clear()
                .type("testuser")
                .focus(PASSWORD_INPUT)
                .clear()
                .type("password123")
                .focus(LOGIN_BUTTON)
                .click();
            
            // Wait for page load and verify
            page.waitFor(WELCOME_MESSAGE, FrameworkEnums.ElementState.VISIBLE)
                .matches("Welcome, testuser!");
            
            // Work with collections
            page.collect(MENU_ITEMS)
                .present("Dashboard")
                .present("Settings")
                .choose(FrameworkEnums.Index.FIRST)
                .click();
            
            // Search functionality
            page.focus(SEARCH_INPUT)
                .type("test query")
                .pause(500);
            
            // Get information
            String currentUrl = page.getUrl();
            String pageTitle = page.getTitle();
            page.log("Current URL: " + currentUrl, "Page Title: " + pageTitle);
            
            // Take screenshot
            page.screenshot("test-complete.png");
            
        } catch (Exception e) {
            page.screenshotAndFail("Test failed: " + e.getMessage());
        } finally {
            page.quit();
        }
    }
}
