package ui;

import core.ui.UiActions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import selenium.SeleniumActions;

import java.nio.file.Path;

import static org.junit.Assert.*;

public class SeleniumActionsTest {
    private UiActions ui;
    private HtmlTestPage.Pages pages;

    @Before
    public void setUp() {
        pages = HtmlTestPage.create();
        // Prefer headless for CI
        ui = new SeleniumActions(true);
    }

    @After
    public void tearDown() {
        if (ui != null) ui.close();
    }

    @AfterClass
    public static void printCoverageLocation() {
        // Print locations where JaCoCo reports are generated when running via Maven
        System.out.println("JaCoCo coverage report: target/site/jacoco/index.html");
        System.out.println("JaCoCo exec data: target/jacoco.exec");
    }

    @Test
    public void testOpenTitleAndUrl() {
        ui.open(pages.page1.toUri().toString());
        assertEquals("Test Page", ui.title());
        assertTrue(ui.url().startsWith("file:"));
    }

    @Test
    public void testExists() {
        ui.open(pages.page1.toUri().toString());
        assertTrue(ui.exists("#text"));
    }

    @Test
    public void testIsVisible() {
        ui.open(pages.page1.toUri().toString());
        assertTrue(ui.isVisible("#text"));
    }

    @Test
    public void testGetText() {
        ui.open(pages.page1.toUri().toString());
        assertEquals("Hello World", ui.getText("#text").trim());
    }

    @Test
    public void testAttribute() {
        ui.open(pages.page1.toUri().toString());
        assertEquals("greeting", ui.attribute("#text", "data-custom"));
    }

    @Test
    public void testValue() {
        ui.open(pages.page1.toUri().toString());
        assertEquals("", ui.value("#name"));
        // Fallback to text content for non-input element
        assertEquals("Hello World", ui.value("#text").trim());
    }

    @Test
    public void testCompose() {
        ui.open(pages.page1.toUri().toString());
        ui.compose("#name", "Bob");
        assertEquals("Bob", ui.value("#name"));
    }

    @Test
    public void testFocus() {
        ui.open(pages.page1.toUri().toString());
        ui.focus("#name");
        assertEquals("true", ui.attribute("#name", "data-focused"));
    }

    @Test
    public void testClick() {
        ui.open(pages.page1.toUri().toString());
        ui.click("#btn");
        assertEquals("Clicked!", ui.getText("#clickResult"));
    }

    @Test
    public void testHover() {
        ui.open(pages.page1.toUri().toString());
        ui.hover("#hoverTarget");
        assertTrue(ui.isVisible("#hoverResult"));
    }

    @Test
    public void testWaitForVisible() {
        ui.open(pages.page1.toUri().toString());
        ui.waitForVisible("#delayed", 4000);
        assertTrue(ui.isVisible("#delayed"));
    }

    @Test
    public void testScreenshot() {
        ui.open(pages.page1.toUri().toString());
        Path shot = Path.of(System.getProperty("java.io.tmpdir"), "selenium-shot-" + System.nanoTime() + ".png");
        ui.screenshot(shot.toString());
        assertTrue(java.nio.file.Files.exists(shot));
    }

    @Test
    public void testBackNavigation() {
        ui.open(pages.page1.toUri().toString());
        // Using direct navigation for reliability in headless Chrome file:// context
        ui.open(pages.page2.toUri().toString());
        assertEquals("Second Page", ui.title());
        ui.back();
        assertEquals("Test Page", ui.title());
    }
}
