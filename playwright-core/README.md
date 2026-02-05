# Playwright Automation Core Framework

A Java-based test automation framework using [Playwright for Java](https://playwright.dev/java/).

This module is a translation of the Selenium-based `com.selenium.qa.automation.core` framework, providing the same API patterns but powered by Microsoft Playwright.

## Features

- **Cross-browser testing**: Chromium, Firefox, and WebKit support
- **Auto-waiting**: Playwright automatically waits for elements to be actionable
- **Built-in assertions**: Element state verification methods
- **Fluent API**: Chainable methods for readable test code
- **DOM traversal**: Navigate up, down, and across the DOM tree
- **Network interception**: Route and mock network requests
- **Screenshot capture**: Automatic screenshots on failures
- **Frame support**: Handle iframes seamlessly
- **Multiple pages/tabs**: Manage browser contexts

## Installation

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>com.core.qa</groupId>
    <artifactId>playwright-automation-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

Install Playwright browsers:

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

## Quick Start

```java
import com.playwright.qa.automation.core.CommonPage;
import com.playwright.qa.automation.core.Enums;
import com.playwright.qa.automation.core.locators.TargetFactory;

public class MyTest extends TargetFactory {

    public void testLogin() {
        CommonPage page = CommonPage.getInstance();
        
        // Open browser and navigate
        page.open(Enums.Browser.CHROMIUM);
        page.go("https://example.com/login");
        
        // Interact with elements
        page.focus(id("username"));
        
        // Use Keyboard for text input
        Keyboard keyboard = Keyboard.getInstance();
        keyboard.fill("myuser@example.com");
        
        page.focus(id("password"));
        keyboard.fill("mypassword");
        
        page.focus(id("login-button"));
        page.click();
        
        // Verify navigation
        page.waitForNavigation();
        assert page.getUrl().contains("/dashboard");
        
        // Cleanup
        page.quit();
    }
}
```

## Key API Differences from Selenium Version

| Selenium API | Playwright API | Notes |
|-------------|----------------|-------|
| `WebDriver` | `Page` | Playwright uses Page object |
| `WebElement` | `Locator` | Locators are re-evaluated on each use |
| `By.id()` | `#id` (CSS) | CSS selectors are preferred |
| `By.xpath()` | `xpath=...` | Prefix with `xpath=` |
| `ExpectedConditions` | Built-in auto-wait | Playwright waits automatically |
| Browser: CHROME, FIREFOX, EDGE, SAFARI | Browser: CHROMIUM, FIREFOX, WEBKIT | Different browser engines |

## Target Factory Methods

Create locators using the `TargetFactory` methods:

```java
// By ID
Target element = id("myId");

// By CSS class
Target element = className("my-class");

// By name attribute
Target element = name("fieldName");

// By text content
Target element = text("Submit");

// By role (Playwright-specific)
Target element = byRole("button", "Submit");

// By test ID (Playwright-specific)
Target element = byTestId("submit-button");

// XPath
Target element = xpath("//div[@class='container']//button");
```

## Common Operations

### Element Focus and Interaction
```java
page.focus(target);           // Wait for and focus element
page.click();                 // Click focused element
page.dblClick();              // Double-click
page.hover();                 // Hover over element
page.clear();                 // Clear input field
```

### Text Input
```java
Keyboard keyboard = Keyboard.getInstance();
keyboard.fill("text");        // Clear and type (recommended)
keyboard.compose("text");     // Type without clearing
keyboard.press("Enter");      // Press special key
```

### Assertions
```java
page.visible();               // Assert element is visible
page.hidden();                // Assert element is hidden
page.enabled();               // Assert element is enabled
page.disabled();              // Assert element is disabled
page.matches("expected");     // Assert text matches
page.contains("partial");     // Assert text contains
```

### Collection Operations
```java
page.collect(target);         // Collect all matching elements
page.choose(1);               // Choose by index (1-based)
page.choose("text");          // Choose by text content
page.choose(Enums.Index.FIRST);
page.size();                  // Get collection size
```

### DOM Navigation
```java
Navigation nav = Navigation.getInstance();
nav.descend();                // Move to first child
nav.descend(target);          // Move to specific child
nav.ascend();                 // Move to parent
nav.sibling();                // Move to next sibling
nav.scroll();                 // Scroll element into view
```

### Frames
```java
page.frame("frameName");      // Switch to frame by name/id
page.frame(0);                // Switch to frame by index
page.frame();                 // Switch to main frame
page.deframe();               // Exit current frame
```

### Playwright-Specific Features
```java
page.waitForNavigation();     // Wait for page navigation
page.waitForLoadState();      // Wait for DOM ready
page.waitForResponse("**/api/**");  // Wait for network response
page.evaluate("document.title");     // Execute JavaScript
page.route("**/api/**", handler);    // Intercept network requests
```

## Configuration

Edit `src/main/resources/properties/playwright.properties`:

```properties
default.browser=CHROMIUM
headless=false
default.timeout=30000
viewport.width=1920
viewport.height=1080
```

## Project Structure

```
playwright-core/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/playwright/qa/automation/core/
    │   │       ├── CommonPage.java
    │   │       ├── CommonPageInterface.java
    │   │       ├── Enums.java
    │   │       ├── Keyboard.java
    │   │       ├── KeyInterface.java
    │   │       ├── Navigation.java
    │   │       ├── NavigationInterface.java
    │   │       ├── PlaywrightCoreException.java
    │   │       ├── locators/
    │   │       │   ├── Target.java
    │   │       │   └── TargetFactory.java
    │   │       ├── logger/
    │   │       │   ├── Logger.java
    │   │       │   ├── LoggerImpl.java
    │   │       │   └── LoggerInterface.java
    │   │       └── utils/
    │   │           ├── Colours.java
    │   │           └── StringUtils.java
    │   └── resources/
    │       └── properties/
    │           └── playwright.properties
    └── test/
        └── java/
            └── com/playwright/qa/automation/core/
                └── CommonPageTest.java
```

## Migration from Selenium

1. Update imports from `com.selenium.qa.automation.core` to `com.playwright.qa.automation.core`
2. Replace `Enums.Browser.CHROME` with `Enums.Browser.CHROMIUM`
3. Update any direct Selenium `By` usage to use `TargetFactory` methods
4. Remove explicit waits - Playwright auto-waits
5. Update XPath selectors to include `xpath=` prefix

## License

MIT

