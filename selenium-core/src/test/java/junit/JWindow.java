package junit;

import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Enums;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JWindow extends CommonPageObject {

    Target tabLink = id("openLinkInNewTab");
    Target otherTabLink = id("openOtherLinkInNewTab");

    Target windowLink = id("openLinkInNewWindow");
    Target otherWindowLink = id("openOtherLinkInNewWindow");

    @BeforeEach
    void setUp() {
        setUpJUnitTests(this);
    }

    @Test
    void shouldSwitchToTab() {

        // given
        String expectedUrl = "https://www.selenium.dev/";

        // when
        focus(tabLink).click().window(Enums.Window.SECOND);
        String actualUrl = getUrl();

        // then
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void shouldSwitchToPrimaryTab() {

        // given
        String expectedUrl = getUrl();

        // when
        focus(tabLink).click().window(Enums.Window.SECOND).window(Enums.Window.HOME);
        String actualUrl = getUrl();

        // then
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void shouldSwitchToTabWithIndex() {

        System.out.println(getUrl());
        // given
        String expectedUrl = "https://www.selenium.dev/";

        // when
        focus(otherTabLink).click().window(Enums.Window.HOME);
        focus(tabLink).click().window(Enums.Window.THIRD);
        String actualUrl = getUrl();

        // then
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void shouldSwitchToWindow() {

        // given
        String expectedUrl = "https://www.selenium.dev/";

        // when
        focus(windowLink).click().window(Enums.Window.SECOND);
        String actualUrl = getUrl();

        // then
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void shouldSwitchToPrimaryWindow() {

        // given
        String expectedUrl = getUrl();

        // when
        focus(windowLink).click().window(Enums.Window.SECOND).window(Enums.Window.HOME);
        String actualUrl = getUrl();

        // then
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void shouldSwitchToWindowWithIndex() {

        // given
        String expectedUrl = "https://www.selenium.dev/";

        // when
        focus(otherWindowLink).click().window(Enums.Window.HOME);
        focus(windowLink).click().window(Enums.Window.THIRD);
        String actualUrl = getUrl();

        // then
        assertEquals(expectedUrl, actualUrl);
    }
}
