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
    public void testBasicInteractions() {
        ui.open(pages.page1.toUri().toString());

        assertEquals("Test Page", ui.title());
        assertTrue(ui.url().startsWith("file:"));

        assertTrue(ui.exists("#text"));
        assertTrue(ui.isVisible("#text"));
        assertEquals("Hello World", ui.getText("#text").trim());
        assertEquals("greeting", ui.attribute("#text", "data-custom"));

        assertEquals("", ui.value("#name"));
        ui.compose("#name", "Bob");
        assertEquals("Bob", ui.value("#name"));

        ui.focus("#name");
        assertEquals("true", ui.attribute("#name", "data-focused"));

        ui.click("#btn");
        assertEquals("Clicked!", ui.getText("#clickResult"));

        ui.hover("#hoverTarget");
        assertTrue(ui.isVisible("#hoverResult"));

        ui.waitForVisible("#delayed", 4000);
        assertTrue(ui.isVisible("#delayed"));

        Path shot = Path.of(System.getProperty("java.io.tmpdir"), "selenium-shot.png");
        ui.screenshot(shot.toString());
        assertTrue(java.nio.file.Files.exists(shot));

        // Using direct navigation for reliability in headless Chrome file:// context
        ui.open(pages.page2.toUri().toString());
        assertEquals("Second Page", ui.title());
        ui.back();
        assertEquals("Test Page", ui.title());
    }
}
