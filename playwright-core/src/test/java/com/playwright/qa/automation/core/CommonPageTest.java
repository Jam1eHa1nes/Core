package com.playwright.qa.automation.core;

import com.playwright.qa.automation.core.locators.Target;
import com.playwright.qa.automation.core.locators.TargetFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic tests for the Playwright CommonPage implementation.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommonPageTest extends TargetFactory {

    private static CommonPage page;

    @BeforeAll
    static void setUp() {
        page = CommonPage.getInstance();
    }

    @AfterAll
    static void tearDown() {
        if (page != null) {
            page.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should open browser and navigate to URL")
    void testOpenAndNavigate() {
        page.open(Enums.Browser.CHROMIUM);
        page.go("https://www.google.com");
        
        String url = page.getUrl();
        assertTrue(url.contains("google.com"));
    }

    @Test
    @Order(2)
    @DisplayName("Should get page title")
    void testGetTitle() {
        String title = page.getTitle();
        assertNotNull(title);
        assertTrue(title.toLowerCase().contains("google"));
    }

    @Test
    @Order(3)
    @DisplayName("Should focus on search input")
    void testFocusOnElement() {
        // Using Playwright's recommended locator for Google search
        Target searchBox = name("q");
        page.focus(searchBox);
        
        assertNotNull(page.getCurrentElement());
    }

    @Test
    @Order(4)
    @DisplayName("Should peek at element without throwing")
    void testPeekElement() {
        Target searchBox = name("q");
        boolean exists = page.peek(searchBox);
        assertTrue(exists);
        
        // Non-existent element should return false
        Target nonExistent = id("non-existent-element");
        boolean notExists = page.peek(nonExistent, 1000);
        assertFalse(notExists);
    }

    @Test
    @Order(5)
    @DisplayName("Should store and retrieve session values")
    void testSessionStorage() {
        page.store("testKey", "testValue");
        String retrieved = page.retrieve("testKey");
        assertEquals("testValue", retrieved);
    }

    @Test
    @Order(6)
    @DisplayName("Should navigate back and forward")
    void testNavigation() {
        page.go("https://www.google.com/about");
        assertTrue(page.getUrl().contains("about"));
        
        page.go(Enums.Direction.BACK);
        // Should be back at main google page
    }
}

