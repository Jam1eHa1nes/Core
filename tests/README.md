# Tests Module

The `tests` module provides a BDD testing framework using Cucumber that can run tests with either Selenium or Playwright backends.

## Overview

This module uses the **Page Object (PO) pattern** with `CommonPageInterface` as the abstraction layer:
- **Steps** contain only Gherkin step definitions and assertions
- **Page Objects (PO)** contain all page interaction logic
- **CommonPageInterface** provides the browser automation API

The framework is selected at runtime via the `-Dautomation.framework` system property.

## Architecture

```
┌────────────────────────────────────────────────────────────────────────┐
│                          Tests Module                                   │
│                                                                         │
│  Feature Files ──> Step Definitions ──> Page Objects (PO)              │
│  (Gherkin)         (assertions only)    (interaction logic)            │
│                                                                         │
│  ┌─────────────┐   ┌─────────────────┐   ┌──────────────────────────┐  │
│  │ *.feature   │──>│ *Steps.java     │──>│ *PO.java extends BasePO  │  │
│  └─────────────┘   └─────────────────┘   └──────────────────────────┘  │
│                                                         │               │
│                                                         ▼               │
│                                          ┌──────────────────────────┐  │
│                                          │ BasePO                   │  │
│                                          │ (CommonPageInterface)    │  │
│                                          └──────────────────────────┘  │
└────────────────────────────────────────────────────────────────────────┘
                                                         │
                                                         │ via CommonPageFactory
                                                         ▼
┌────────────────────────────────────────────────────────────────────────┐
│                        Common-Core Module                               │
│  ┌────────────────────┐  ┌────────────┐  ┌──────────────────────────┐  │
│  │ CommonPageInterface│  │ VMArgs     │  │ CommonPageFactory        │  │
│  └────────────────────┘  └────────────┘  └──────────────────────────┘  │
└────────────────────────────────────────────────────────────────────────┘
                                                         │
              ┌──────────────────────────────────────────┴───────────────┐
              │                                                          │
              ▼                                                          ▼
┌─────────────────────────────┐                      ┌─────────────────────────────┐
│      Selenium-Core          │                      │      Playwright-Core        │
│  ┌───────────────────────┐  │                      │  ┌───────────────────────┐  │
│  │ CommonPage implements │  │                      │  │ CommonPage implements │  │
│  │ CommonPageInterface   │  │                      │  │ CommonPageInterface   │  │
│  └───────────────────────┘  │                      │  └───────────────────────┘  │
└─────────────────────────────┘                      └─────────────────────────────┘
```

## Project Structure

```
tests/
├── pom.xml
├── README.md
└── src/test/
    ├── java/com/core/qa/automation/tests/
    │   ├── hooks/
    │   │   └── Hooks.java              # Cucumber lifecycle hooks
    │   ├── po/                          # Page Objects
    │   │   ├── BasePO.java             # Base class for all POs
    │   │   ├── NavigationPO.java       # Navigation actions
    │   │   └── SearchPO.java           # Search functionality
    │   ├── runners/
    │   │   └── TestRunner.java         # JUnit 5 Cucumber runner
    │   └── steps/                       # Step definitions (thin layer)
    │       ├── NavigationSteps.java
    │       └── SearchSteps.java
    └── resources/
        ├── cucumber.properties
        ├── features/
        │   └── search.feature
        └── properties/
            └── test.properties
```

## Running Tests

### With Selenium (Default)

```bash
# Run all tests
mvn test -pl tests

# Run with Selenium explicitly  
mvn test -pl tests -Dautomation.framework=selenium

# Run specific tags
mvn test -pl tests -Dcucumber.filter.tags="@smoke"

# Run in headless mode
mvn test -pl tests -Dheadless.mode=true
```

### With Playwright

```bash
# Run all tests with Playwright
mvn test -pl tests -Dautomation.framework=playwright

# Run specific tags with Playwright
mvn test -pl tests -Dautomation.framework=playwright -Dcucumber.filter.tags="@smoke"
```

## Writing Tests

### 1. Create a Feature File

```gherkin
# src/test/resources/features/login.feature
@login
Feature: User Login

  Scenario: Successful login
    Given I open "https://example.com/login"
    When I enter username "user@test.com"
    And I enter password "secret"
    And I click login button
    Then I should see the dashboard
```

### 2. Create a Page Object (PO)

Page Objects contain all page interaction logic:

```java
package com.core.qa.automation.tests.po;

public class LoginPO extends BasePO {

    public void enterUsername(String username) {
        log("Entering username: " + username);
        // Use JavaScript or CommonPageInterface methods
        page.javascript("document.querySelector('#username').value = '" + username + "'");
    }

    public void enterPassword(String password) {
        log("Entering password");
        page.javascript("document.querySelector('#password').value = '" + password + "'");
    }

    public void clickLoginButton() {
        log("Clicking login button");
        page.javascript("document.querySelector('#login-btn').click()");
    }

    public boolean isDashboardDisplayed() {
        return pageContainsText("Welcome") || urlContains("/dashboard");
    }
}
```

### 3. Create Step Definitions

Steps should be thin - only containing the Gherkin mapping and assertions:

```java
package com.core.qa.automation.tests.steps;

import com.core.qa.automation.tests.po.LoginPO;
import io.cucumber.java.en.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    private final LoginPO loginPage = new LoginPO();

    @Given("I open {string}")
    public void iOpen(String url) {
        loginPage.openBrowser();
        loginPage.navigateTo(url);
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click login button")
    public void iClickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("I should see the dashboard")
    public void iShouldSeeTheDashboard() {
        assertThat(loginPage.isDashboardDisplayed())
            .as("Dashboard should be displayed")
            .isTrue();
    }
}
```

## Configuration

### System Properties (VMArgs)

| Property | Description | Default |
|----------|-------------|---------|
| `automation.framework` | Framework: `selenium` or `playwright` | `selenium` |
| `headless.mode` | Run browser in headless mode | `false` |
| `browser.screenshot.onerror` | Take screenshot on test failure | `true` |
| `browser.keep.open` | Keep browser open after test | `false` |
| `browser.window` | Browser window index (1-based) | `1` |

## Best Practices

1. **Steps are thin**: Steps should only contain Gherkin mapping and assertions
2. **POs contain logic**: All page interaction logic goes in Page Objects
3. **Extend BasePO**: All Page Objects should extend `BasePO`
4. **Use CommonPageInterface**: Access browser via `page` field from `BasePO`
5. **Never reference frameworks directly**: No Selenium/Playwright imports in steps or POs
6. **Use JavaScript when needed**: For complex interactions, use `page.javascript()`
7. **Log actions**: Use `log()` method in POs for debugging

