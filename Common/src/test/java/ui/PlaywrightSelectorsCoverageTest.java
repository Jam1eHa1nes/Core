package ui;

import core.ui.TargetFactory;
import core.ui.UiActions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import playwright.PlaywrightActions;

import static org.junit.Assert.*;

/**
 * Additional targeted tests to lift PlaywrightActions coverage over 95% by
 * exercising selector conversion branches and error paths.
 */
public class PlaywrightSelectorsCoverageTest {
    private UiActions ui;
    private HtmlTestPage.Pages pages;

    @Before
    public void setUp() {
        pages = HtmlTestPage.create();
        ui = new PlaywrightActions(true);
        ui.open(pages.page1.toUri().toString());
    }

    @After
    public void tearDown() {
        if (ui != null) ui.close();
    }

    @Test(expected = IllegalStateException.class)
    public void requireContextThrowsWhenNotFocused() {
        // Call action that requires prior focus; should throw
        new PlaywrightActions(true).click();
    }

    @Test
    public void coversAllSelectorStrategies() {
        // ID
        assertTrue(ui.exists(TargetFactory.id("text")));

        // NAME
        assertTrue(ui.exists(TargetFactory.name("username")));

        // CLASS_NAME
        assertTrue(ui.exists(TargetFactory.className("sample-class")));

        // TAG_NAME (there is at least one <p>)
        assertTrue(ui.exists(TargetFactory.tag("p")));

        // LINK_TEXT and PARTIAL_LINK_TEXT
        assertTrue(ui.exists(TargetFactory.linkText("Go to Page 2")));
        assertTrue(ui.exists(TargetFactory.partialLinkText("Page 2")));

        // TEXT selector (visible text)
        assertTrue(ui.exists(TargetFactory.text("Hovered!")));

        // DATA_TEST_ID
        assertTrue(ui.exists(TargetFactory.dataTestId("test-elem")));

        // ROLE
        assertTrue(ui.exists(TargetFactory.role("dialog")));

        // XPATH absolute matching the link
        assertTrue(ui.exists(TargetFactory.xpath("//a[normalize-space(text())='Go to Page 2']")));
    }

    @Test
    public void escapeBranchesAreExercised() {
        // These now correspond to elements added to the HtmlTestPage, ensuring the selector
        // building code executes with single quotes and succeeds.
        assertTrue(ui.exists(TargetFactory.name("user'o")));
        assertTrue(ui.exists(TargetFactory.linkText("Click 'Me'")));
        assertTrue(ui.exists(TargetFactory.partialLinkText("'quote' inside")));
    }
}
