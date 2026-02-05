package com.core.qa.automation.tests.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Cucumber test runner using JUnit 4.
 * This class triggers Cucumber feature discovery from src/test/resources/features.
 * 
 * Run all tests:
 *   mvn test -pl tests
 * 
 * Run with Selenium:
 *   mvn test -pl tests -Dautomation.framework=selenium
 * 
 * Run with Playwright:
 *   mvn test -pl tests -Dautomation.framework=playwright
 * 
 * Run specific tags:
 *   mvn test -pl tests -Dcucumber.filter.tags="@smoke"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.core.qa.automation.tests.steps", "com.core.qa.automation.tests.hooks"},
    plugin = {
        "pretty",
        "json:target/cucumber-reports/cucumber.json",
        "html:target/cucumber-reports/cucumber.html",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }
)
public class TestRunner {
}
