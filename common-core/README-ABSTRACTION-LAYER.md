# Framework-Agnostic Automation Abstraction Layer

This document explains how to write tests that work with both Playwright and Selenium without changing your test code.

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                          tests/                                  │
│  (Your test code uses PageActions & LocatorFactory)             │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                       common-core/                               │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐  │
│  │ PageActions │  │   Locator   │  │    FrameworkEnums       │  │
│  │ (interface) │  │ (interface) │  │ (Browser, State, etc.)  │  │
│  └─────────────┘  └─────────────┘  └─────────────────────────┘  │
│  ┌─────────────┐  ┌──────────────────────────────────────────┐  │
│  │ PageFactory │  │           LocatorFactory                 │  │
│  └─────────────┘  └──────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
              ┌───────────────┴───────────────┐
              ▼                               ▼
┌─────────────────────────┐     ┌─────────────────────────┐
│    playwright-core/     │     │     selenium-core/      │
│ PlaywrightPageActions   │     │  SeleniumPageActions    │
│ (implements PageActions)│     │ (implements PageActions)│
└─────────────────────────┘     └─────────────────────────┘
```

## Quick Start

### 1. Write Tests Using Common Interfaces

```java
import com.core.qa.automation.common.*;
import com.core.qa.automation.common.locators.*;

public class MyTest {
    
    // Define locators using LocatorFactory
    private static final Locator LOGIN_BTN = LocatorFactory.id("login-btn");
    private static final Locator USERNAME = LocatorFactory.css("input[name='user']");
    private static final Locator ERROR_MSG = LocatorFactory.text("Invalid credentials");
    
    public void testLogin() {
        // Get PageActions - automatically uses available framework
        PageActions page = PageFactory.create();
        
        page.open(FrameworkEnums.Browser.CHROME)
            .go("https://myapp.com")
            .focus(USERNAME)
            .type("testuser")
            .focus(LOGIN_BTN)
            .click()
            .waitFor(ERROR_MSG, FrameworkEnums.ElementState.VISIBLE);
        
        page.quit();
    }
}
```

### 2. Configure Framework in pom.xml

**For Playwright:**
```xml
<dependency>
    <groupId>com.core.qa.automation</groupId>
    <artifactId>playwright-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

**For Selenium:**
```xml
<dependency>
    <groupId>com.core.qa.automation</groupId>
    <artifactId>selenium-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 3. Override Framework at Runtime (Optional)

```bash
# Using system property
mvn test -Dautomation.framework=playwright

# Using environment variable
export AUTOMATION_FRAMEWORK=selenium
mvn test
```

## Available Locator Types

```java
// By ID
LocatorFactory.id("element-id")

// By CSS selector
LocatorFactory.css("div.class-name > span")

// By XPath
LocatorFactory.xpath("//button[@type='submit']")

// By text content
LocatorFactory.text("Click me")
LocatorFactory.partialText("Click")

// By attributes
LocatorFactory.name("email")
LocatorFactory.placeholder("Enter email...")
LocatorFactory.title("Submit form")
LocatorFactory.testId("login-button")

// By tag with text
LocatorFactory.tagWithText("button", "Submit")
LocatorFactory.tagContainsText("div", "Welcome")

// By ARIA role
LocatorFactory.role("button")

// By label
LocatorFactory.label("Email Address")

// Custom attributes
LocatorFactory.attribute("data-custom", "value")
LocatorFactory.dataAttribute("endpoint", "/api/users")
```

## Available Page Actions

### Browser Management
```java
page.open()                              // Open default browser
page.open(FrameworkEnums.Browser.FIREFOX) // Open specific browser
page.go("https://example.com")           // Navigate to URL
page.go(FrameworkEnums.Direction.BACK)   // Navigate back
page.refresh()                           // Refresh page
page.close()                             // Close window/tab
page.quit()                              // Quit browser
page.fullScreen()                        // Full screen mode
page.maximise()                          // Maximize window
```

### Element Interaction
```java
page.focus(locator)              // Focus on element
page.click()                     // Click focused element
page.dblClick()                  // Double-click
page.type("text")                // Type into element
page.fill("text")                // Clear and type
page.clear()                     // Clear element
page.hover()                     // Hover over element
page.dragAndDrop(source, target) // Drag and drop
page.uploadFile("/path/to/file") // Upload file
```

### Element Information
```java
String text = page.getText();
String value = page.get(FrameworkEnums.ElementTrait.VALUE);
String attr = page.getAttribute("data-id");
String url = page.getUrl();
String title = page.getTitle();
```

### Collections
```java
page.collect(locator)                    // Collect elements
int count = page.size()                  // Get count
page.choose(0)                           // Select by index
page.choose("Option Text")               // Select by text
page.choose(FrameworkEnums.Index.FIRST)  // Select first
page.choose(FrameworkEnums.Index.LAST)   // Select last
```

### Assertions
```java
page.matches("Exact text")               // Assert exact match
page.contains("partial")                 // Assert contains
page.assertState(FrameworkEnums.ElementState.VISIBLE)
page.present("text")                     // Assert in collection
page.absent("text")                      // Assert not in collection
```

### Waits
```java
page.waitFor(locator, FrameworkEnums.ElementState.VISIBLE)
page.waitFor(locator, FrameworkEnums.ElementState.CLICKABLE, 5000)
page.pause(1000)                         // Hard wait (ms)
```

### Frames & Windows
```java
page.frame("frameName")                  // Switch to frame
page.frame(0)                            // Switch by index
page.defaultContent()                    // Back to main
page.window(FrameworkEnums.Window.NEW)   // Switch window
page.window(1)                           // Switch by index
```

### JavaScript
```java
Object result = page.executeScript("return document.title");
page.executeScript("arguments[0].click()", element);
```

### Screenshots
```java
page.screenshot("filename.png")
page.screenshotAndFail("Test failed!")
```

## Extending the Framework

### Adding New Locator Types

1. Add to `Locator.Type` enum in common-core
2. Add factory method in `LocatorFactory`
3. Implement mapping in `PlaywrightPageActions.toTarget()`
4. Implement mapping in `SeleniumPageActions.toBy()` (if needed)

### Adding New Page Actions

1. Add method to `PageActions` interface in common-core
2. Implement in `PlaywrightPageActions`
3. Implement in `SeleniumPageActions`

## Benefits

1. **Single Test Codebase** - Write tests once, run with any framework
2. **Easy Framework Switching** - Change dependency, not code
3. **Consistent API** - Same methods regardless of framework
4. **Type Safety** - Compile-time checking of locators and enums
5. **IDE Support** - Full autocomplete and documentation
