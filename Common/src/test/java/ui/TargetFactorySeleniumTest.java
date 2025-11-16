package ui;

import core.ui.TargetFactory;
import core.ui.UiActions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import selenium.SeleniumActions;

import static org.junit.Assert.*;

/**
 * Verifies all TargetFactory strategies work end-to-end with SeleniumActions.
 */
public class TargetFactorySeleniumTest {
    private UiActions ui;
    private HtmlTestPage.Pages pages;

    @Before
    public void setup() {
        pages = HtmlTestPage.create();
        ui = new SeleniumActions(true);
        ui.open(pages.page1.toUri().toString());
    }

    @After
    public void teardown() {
        if (ui != null) ui.close();
    }

    @Test
    public void css_shouldLocateElement() {
        assertTrue(ui.exists(TargetFactory.css("#text")));
        assertEquals("Hello World", ui.getText(TargetFactory.css("#text")).trim());
    }

    @Test
    public void xpath_shouldLocateElement() {
        assertEquals("Hello World", ui.getText(TargetFactory.xpath("//*[@id='text']")).trim());
    }

    @Test
    public void id_shouldLocateElement() {
        assertTrue(ui.exists(TargetFactory.id("btn")));
    }

    @Test
    public void name_shouldLocateElement() {
        assertTrue(ui.exists(TargetFactory.name("username")));
    }

    @Test
    public void className_shouldLocateElement() {
        assertTrue(ui.exists(TargetFactory.className("sample-class")));
    }

    @Test
    public void tag_shouldLocateElement() {
        assertTrue(ui.exists(TargetFactory.tag("button")));
    }

    @Test
    public void linkText_shouldLocateAnchor() {
        assertTrue(ui.exists(TargetFactory.linkText("Go to Page 2")));
    }

    @Test
    public void partialLinkText_shouldLocateAnchor() {
        assertTrue(ui.exists(TargetFactory.partialLinkText("Page 2")));
    }

    @Test
    public void text_shouldLocateByTextFallback() {
        assertTrue(ui.exists(TargetFactory.text("Hello World")));
    }

    @Test
    public void dataTestId_shouldLocateElement() {
        assertTrue(ui.exists(TargetFactory.dataTestId("test-elem")));
        assertEquals("DataTestId", ui.getText(TargetFactory.dataTestId("test-elem")).trim());
    }

    @Test
    public void role_shouldLocateElement() {
        assertTrue(ui.exists(TargetFactory.role("dialog")));
        assertEquals("Dialog Role", ui.getText(TargetFactory.role("dialog")).trim());
    }
}
