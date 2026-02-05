package com.core.qa.automation.tests.hooks;

import com.core.qa.automation.common.CommonPageFactory;
import com.core.qa.automation.common.CommonPageInterface;
import com.core.qa.automation.common.VMArgs;
import com.core.qa.automation.common.logger.Logger;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cucumber hooks for test lifecycle management.
 * These hooks run before/after scenarios and can be used for setup/teardown.
 * Includes Allure reporting integration for screenshots on failure.
 */
public class Hooks {

    private static final Logger logger = new Logger();
    private static final String SCREENSHOTS_DIR = "target/screenshots";

    @BeforeAll
    public static void beforeAll() {
        logger.log("========================================");
        logger.log("Starting Test Suite");
        logger.log("Framework: " + VMArgs.getFrameworkName());
        logger.log("Headless: " + VMArgs.headless);
        logger.log("========================================");
        
        // Ensure screenshots directory exists
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
        } catch (Exception e) {
            logger.warn("Could not create screenshots directory: " + e.getMessage());
        }
    }

    @Before
    public void before(Scenario scenario) {
        logger.log("----------------------------------------");
        logger.log("Starting Scenario: " + scenario.getName());
        logger.log("Tags: " + scenario.getSourceTagNames());
        logger.log("----------------------------------------");
    }

    @After
    public void after(Scenario scenario) {
        logger.log("----------------------------------------");
        logger.log("Scenario: " + scenario.getName());
        logger.log("Status: " + scenario.getStatus());
        logger.log("----------------------------------------");
        
        // Take screenshot on failure before closing browser
        if (scenario.isFailed()) {
            logger.warn("Scenario failed: " + scenario.getName());
            
            // Take screenshot and attach to Allure report
            if (VMArgs.screenshotOnError) {
                attachScreenshotToAllure(scenario);
            }
        }
        
        // Close the browser after each scenario to prevent browser spam
        closeBrowser();
    }

    /**
     * Closes the browser after test completion.
     * This prevents multiple browser windows from piling up.
     */
    private void closeBrowser() {
        try {
            CommonPageInterface page = CommonPageFactory.create();
            if (page != null) {
                // Check if browser should be kept open for debugging
                if (!VMArgs.browserKeepOpen) {
                    logger.log("Closing browser...");
                    page.quit();
                    // Reset the factory so next scenario gets a fresh browser
                    CommonPageFactory.reset();
                } else {
                    logger.log("Browser kept open (browser.keep.open=true)");
                }
            }
        } catch (Exception e) {
            logger.warn("Error closing browser: " + e.getMessage());
        }
    }

    /**
     * Takes a screenshot and attaches it to the Allure report.
     */
    private void attachScreenshotToAllure(Scenario scenario) {
        try {
            // Generate screenshot filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            String filename = String.format("%s_%s.png", safeName, timestamp);
            Path screenshotPath = Paths.get(SCREENSHOTS_DIR, filename);
            
            // Try to get the CommonPage instance and take screenshot
            CommonPageInterface page = CommonPageFactory.create();
            if (page != null) {
                page.takeScreenShotAndExit(screenshotPath.toString());
                
                // If file exists, attach to Allure
                if (Files.exists(screenshotPath)) {
                    byte[] screenshotBytes = Files.readAllBytes(screenshotPath);
                    Allure.addAttachment("Screenshot on Failure", "image/png", 
                            new ByteArrayInputStream(screenshotBytes), ".png");
                    
                    // Also embed in Cucumber report
                    scenario.attach(screenshotBytes, "image/png", "Screenshot");
                    logger.log("Screenshot attached to report: " + filename);
                }
            }
        } catch (Exception e) {
            logger.warn("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @AfterAll
    public static void afterAll() {
        // Reset the CommonPageFactory to clean up
        CommonPageFactory.reset();
        
        logger.log("========================================");
        logger.log("Test Suite Complete");
        logger.log("========================================");
    }

    /**
     * Returns the current automation framework being used.
     */
    public static String getFramework() {
        return VMArgs.getFrameworkName();
    }

    /**
     * Check if running with Selenium.
     */
    public static boolean isSelenium() {
        return VMArgs.isSelenium();
    }

    /**
     * Check if running with Playwright.
     */
    public static boolean isPlaywright() {
        return VMArgs.isPlaywright();
    }
}

