# Common Core Module

The `common-core` module provides shared utilities, interfaces, and services used by both `selenium-core` and `playwright-core` automation modules.

## Overview

This module contains all browser-independent utilities that can be shared across automation frameworks.

### Core Interfaces
- **CommonPageInterface**: The base interface that both Selenium and Playwright CommonPage implementations extend
- **KeyInterface**: Interface for keyboard interactions (typing, key presses, key combinations)
- **NavigationInterface**: Interface for DOM navigation (ascend, descend, traverse, scroll)
- **StorageProvider**: Interface for storage operations (retrieve/store)

### Factory & Configuration
- **CommonPageFactory**: Factory for creating CommonPageInterface instances based on framework
- **VMArgs**: Common VM arguments for framework selection and browser configuration

### Enums
- **Enums**: Shared enums for both frameworks including:
  - `Index`, `Level`, `NodeEnum` - For element positioning
  - `Tag` - HTML tag names
  - `Browser` - Browser types (Chrome, Chromium, Firefox, Edge, Safari, WebKit)
  - `ElementTrait`, `ElementStatus`, `ElementState` - Element properties
  - `Direction`, `Event`, `AlertAction` - Actions and events
  - `Attribute`, `Type`, `Window` - HTML attributes and types

### HTTP Services
- **HttpService**: REST Assured-based HTTP client for API testing
- **HttpServiceInterface**: Interface for HTTP operations

### File Services
- **ExcelServices**: Apache POI-based Excel file reader/writer (.xls and .xlsx)
- **PDFServices**: Apache PDFBox-based PDF file reader

### Logger
- **Logger**: Console logging with ANSI color support
- **LoggerImpl**: Logger implementation
- **LoggerInterface**: Logging interface

### Utilities
- **Colours**: ANSI color codes for console output
- **StringUtils**: Common string operations (random strings, null checks, etc.)
- **DateUtils**: Date comparison and time difference calculations
- **DateTimeFormatterUtils**: Common DateTimeFormatter patterns
- **FileUtils**: File read/write operations
- **ListUtils**: List operations (null checks, aggregation, etc.)
- **ObjUtils**: Object utilities (null checks, coalesce)
- **CoreUtils**: Validation utilities (throwOnNull, throwOnEmpty, etc.)
- **PropertiesReader**: Read .properties files from resources
- **StopWatch**: Measure elapsed time
- **WindowUtils**: Screen/window position utilities
- **ReflectionUtils**: Reflection-based method invocation

### Exception
- **AutomationException**: Common exception class for all modules

## Usage

### Using CommonPageFactory

```java
// Get CommonPageInterface instance based on -Dautomation.framework property
CommonPageInterface page = CommonPageFactory.create();

// Open browser and navigate
page.open();
page.go("https://example.com");

// Interact with elements (via framework-specific implementation)
page.click();
page.javascript("return document.title");

// Clean up
page.quit();
```

### Using VMArgs

```java
// Check which framework is active
if (VMArgs.isSelenium()) {
    // Selenium-specific code
}

if (VMArgs.isPlaywright()) {
    // Playwright-specific code
}

// Access common settings
boolean headless = VMArgs.headless;
boolean screenshotOnError = VMArgs.screenshotOnError;
```

### Using Common Enums

```java
import com.core.qa.automation.common.Enums.*;

// Browser selection
Browser browser = VMArgs.isPlaywright() ? Browser.CHROMIUM : Browser.CHROME;

// Element traits
ElementTrait trait = ElementTrait.TEXT;

// Index selection
Index index = Index.FIRST;
```

### In Selenium-Core

```java
// CommonPage extends the common interface
public class CommonPage implements CommonPageInterface {
    // Implements all common methods plus Selenium-specific ones
}

// Use HTTP service with Selenium's storage
HttpService httpService = HttpService.getInstance();
httpService.setStorageProvider(CommonPage.getInstance());
Response response = httpService.get("/api/users");
```

### In Playwright-Core

```java
// CommonPage extends the common interface
public class CommonPage implements CommonPageInterface {
    // Implements all common methods plus Playwright-specific ones
}

// Use HTTP service with Playwright's storage
HttpService httpService = HttpService.getInstance();
httpService.setStorageProvider(CommonPage.getInstance());
Response response = httpService.post("/api/data", jsonBody);
```

### Standalone Usage (No Browser)

```java
// HTTP Service can be used without browser automation
HttpService httpService = HttpService.getInstance();
httpService.setBaseURI("https://api.example.com");
httpService.setUserName("user");
httpService.setPassword("pass");

Response response = httpService.get("/users");

// Excel Service
ExcelServices excel = new ExcelServices();
excel.open("/path/to/dir", "data.xlsx");
String value = excel.getCellValue("A1");
excel.close();

// PDF Service
PDFServices pdf = new PDFServices();
pdf.open("/path/to/dir", "document.pdf");
boolean hasKeyword = pdf.contentPresent("important");
pdf.close();
```

## Module Structure

```
common-core/
├── src/main/java/com/core/qa/automation/common/
│   ├── CommonPageInterface.java      # Base interface for page operations
│   ├── StorageProvider.java          # Storage interface
│   ├── exception/
│   │   └── AutomationException.java  # Common exception
│   ├── http/
│   │   ├── HttpService.java          # REST Assured HTTP client
│   │   └── HttpServiceInterface.java # HTTP interface
│   ├── file/
│   │   ├── excel/
│   │   │   ├── ExcelServices.java
│   │   │   └── ExcelServicesInterface.java
│   │   └── pdf/
│   │       ├── PDFServices.java
│   │       └── PDFServicesInterface.java
│   ├── logger/
│   │   ├── Logger.java
│   │   ├── LoggerImpl.java
│   │   └── LoggerInterface.java
│   └── utils/
│       ├── Colours.java              # ANSI color codes
│       └── StringUtils.java          # String utilities
└── pom.xml
```

## Dependencies

- REST Assured (HTTP client)
- Apache POI (Excel support)
- Apache PDFBox (PDF support)
- Lombok
- JUnit 5

## Adding to Your Project

Add the dependency to your module's `pom.xml`:

```xml
<dependency>
    <groupId>com.core.qa</groupId>
    <artifactId>common-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

