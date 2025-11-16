package ui;

import core.ui.UiActions;
import core.ui.TargetFactory;
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
        assertTrue(ui.exists(TargetFactory.css("#text")));
    }

    @Test
    public void testIsVisible() {
        ui.open(pages.page1.toUri().toString());
        assertTrue(ui.isVisible(TargetFactory.css("#text")));
    }

    @Test
    public void testGetText() {
        ui.open(pages.page1.toUri().toString());
        assertEquals("Hello World", ui.getText(TargetFactory.css("#text")).trim());
    }

    @Test
    public void testAttribute() {
        ui.open(pages.page1.toUri().toString());
        assertEquals("greeting", ui.attribute(TargetFactory.css("#text"), "data-custom"));
    }

    @Test
    public void testValue() {
        ui.open(pages.page1.toUri().toString());
        assertEquals("", ui.value(TargetFactory.css("#name")));
        // Fallback to text content for non-input element
        assertEquals("Hello World", ui.value(TargetFactory.css("#text")).trim());
    }

    @Test
    public void testCompose() {
        ui.open(pages.page1.toUri().toString());
        ui.compose(TargetFactory.css("#name"), "Bob");
        assertEquals("Bob", ui.value(TargetFactory.css("#name")));
    }

    @Test
    public void testFocus() {
        ui.open(pages.page1.toUri().toString());
        ui.focus(TargetFactory.css("#name"));
        assertEquals("true", ui.attribute(TargetFactory.css("#name"), "data-focused"));
    }

    @Test
    public void testClick() {
        ui.open(pages.page1.toUri().toString());
        ui.click(TargetFactory.css("#btn"));
        assertEquals("Clicked!", ui.getText(TargetFactory.css("#clickResult")));
    }

    @Test
    public void testHover() {
        ui.open(pages.page1.toUri().toString());
        ui.hover(TargetFactory.css("#hoverTarget"));
        assertTrue(ui.isVisible(TargetFactory.css("#hoverResult")));
    }

    @Test
    public void testWaitForVisible() {
        ui.open(pages.page1.toUri().toString());
        ui.waitForVisible(TargetFactory.css("#delayed"), 4000);
        assertTrue(ui.isVisible(TargetFactory.css("#delayed")));
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

    @Test
    public void testForwardNavigation() {
        ui.open(pages.page1.toUri().toString());
        ui.open(pages.page2.toUri().toString());
        assertEquals("Second Page", ui.title());
        ui.back();
        assertEquals("Test Page", ui.title());
        ui.forward();
        assertEquals("Second Page", ui.title());
    }

    @Test
    public void testRefreshClearsState() {
        ui.open(pages.page1.toUri().toString());
        ui.compose(TargetFactory.css("#name"), "Bob");
        assertEquals("Bob", ui.value(TargetFactory.css("#name")));
        ui.refresh();
        assertEquals("", ui.value(TargetFactory.css("#name")));
    }

    @Test
    public void testClear() {
        ui.open(pages.page1.toUri().toString());
        ui.compose(TargetFactory.css("#name"), "Data");
        assertEquals("Data", ui.value(TargetFactory.css("#name")));
        ui.clear(TargetFactory.css("#name"));
        assertEquals("", ui.value(TargetFactory.css("#name")));
    }

    @Test
    public void testDoubleClick() {
        ui.open(pages.page1.toUri().toString());
        ui.doubleClick(TargetFactory.css("#dbl"));
        assertEquals("Double!", ui.getText(TargetFactory.css("#dblResult")));
    }

    @Test
    public void testSelectByTextAndValue() {
        ui.open(pages.page1.toUri().toString());
        ui.selectByText(TargetFactory.css("#sel"), "Label Two");
        assertEquals("v2", ui.getText(TargetFactory.css("#selValue")));
        ui.selectByValue(TargetFactory.css("#sel"), "v1");
        assertEquals("v1", ui.getText(TargetFactory.css("#selValue")));
    }

    @Test
    public void testWaitForHidden() {
        ui.open(pages.page1.toUri().toString());
        ui.waitForHidden(TargetFactory.css("#toHide"), 4000);
        assertFalse(ui.isVisible(TargetFactory.css("#toHide")));
    }

    @Test
    public void testScrollIntoView() {
        ui.open(pages.page1.toUri().toString());
        ui.scrollIntoView(TargetFactory.css("#bottom"));
        assertTrue(ui.exists(TargetFactory.css("#bottom")));
    }

    @Test
    public void testPressString() {
        ui.open(pages.page1.toUri().toString());
        ui.focus(TargetFactory.css("#key"));
        ui.press(TargetFactory.css("#key"), "Enter");
        assertEquals("Enter", ui.getText(TargetFactory.css("#keyResult")));
    }

    @Test
    public void testSetCheckedAndUploadFile() throws Exception {
        ui.open(pages.page1.toUri().toString());
        ui.setChecked(TargetFactory.css("#chk"), true);
        assertEquals("true", ui.attribute(TargetFactory.css("#chk"), "data-checked"));
        ui.setChecked(TargetFactory.css("#chk"), false);
        assertEquals("false", ui.attribute(TargetFactory.css("#chk"), "data-checked"));

        java.nio.file.Path tmp = java.nio.file.Files.createTempFile("upl-", ".txt");
        java.nio.file.Files.writeString(tmp, "data");
        ui.uploadFile(TargetFactory.css("#file"), tmp.toString());
        assertEquals(tmp.getFileName().toString(), ui.attribute(TargetFactory.css("#file"), "data-file-name"));
        assertEquals("true", ui.attribute(TargetFactory.css("#file"), "data-file-loaded"));
    }

    // ----- New selector-less context tests -----

    @Test
    public void testClickWithoutSelectorAfterFind() {
        ui.open(pages.page1.toUri().toString());
        ui.focus(TargetFactory.css("#btn"));
        ui.click();
        assertEquals("Clicked!", ui.getText(TargetFactory.css("#clickResult")));
    }

    @Test
    public void testComposeAndReadWithoutSelector() {
        ui.open(pages.page1.toUri().toString());
        ui.focus(TargetFactory.css("#name"));
        ui.compose("Daisy");
        assertEquals("Daisy", ui.value(TargetFactory.css("#name")));
        // Now read using context
        ui.focus(TargetFactory.css("#text"));
        assertEquals("Hello World", ui.getText().trim());
    }

    @Test
    public void testSelectAndPressWithoutSelector() {
        ui.open(pages.page1.toUri().toString());
        ui.focus(TargetFactory.css("#sel"));
        ui.selectByText("Label Two");
        assertEquals("v2", ui.getText(TargetFactory.css("#selValue")));
        ui.focus(TargetFactory.css("#key")); // sets context too
        ui.press("Enter");
        assertEquals("Enter", ui.getText(TargetFactory.css("#keyResult")));
    }
}
