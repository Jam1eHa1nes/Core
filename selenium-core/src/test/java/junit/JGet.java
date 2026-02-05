package junit;

import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Enums;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.selenium.qa.automation.core.Enums.Tag.A;
import static com.selenium.qa.automation.core.Enums.Tag.DIV;
import static com.selenium.qa.automation.core.Enums.Tag.H1;
import static com.selenium.qa.automation.core.Enums.Tag.OPTION;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JGet extends CommonPageObject {

    @BeforeAll
    void setUp() {
        setUpJUnitTests(this);
    }

    @ParameterizedTest()
    @CsvSource({
            "Selected, SELECTED, TRUE",
            "NotSelected, SELECTED, FALSE",
            "Enabled, ENABLED, TRUE",
            "Disabled, ENABLED, FALSE",
            "Displayed, DISPLAYED, TRUE",
            "NotDisplayed, DISPLAYED, FALSE"
    })

    @DisplayName("Test get element attribute")
    void shouldGetAttributes(String targetId, Enums.ElementTrait elementTrait, String expected) {

        // given
        Target target = id("getAttributeTest" + targetId);

        // when
        String actual = focus(target).get(elementTrait);

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test get element attribute")
    public void testAttribute() {
        Target target = id("identifier");
        focus(target);
        assertEquals(get(ATTRIBUTES),"{id=identifier, placeholder=type your id here, type=text}");
    }
    @Test
    @DisplayName("Test get element id")
    public void testId() {
        Target target = classNames("class2 class3");
        focus(target);
        assertTrue(get(ID).equals("class-names"));
    }

    @Test
    @DisplayName("Test get element content")
    public void testContent() {
        Target target = id("inner1");
        focus(target);
        assertTrue(get(CONTENT).equals("this is inner1"));
    }

    @Test
    @DisplayName("Test get element text")
    public void testText() {
        Target target = tagWithId(DIV,"inner2");
        focus(target);
        assertEquals(get(TEXT),"Inner2");
    }

    @Test
    @DisplayName("Test get element tag")
    public void testTag() {
        Target target = id("inner3");
        focus(target);
        assertEquals(get(TAG),"div");
    }

    @Test
    @DisplayName("Test get element class")
    public void testClass() {
        Target target = id("button-covering-div");
        focus(target);
        assertEquals(get(CLASS),"button-covering");
    }

    @Test
    @DisplayName("Test get element value")
    public void testValue() {
        Target target = tagWithText(OPTION,"Monday");
        focus(target);
        assertEquals(get(VALUE),"monday");
    }

    @Test
    @DisplayName("Test get element name")
    public void testName() {
        Target target = id("days");
        focus(target);
        assertEquals(get(NAME),"days");
    }

    @Test
    @DisplayName("Test get element alt")
    public void testAlt() {
        Target target = id("logo");
        focus(target);
        assertEquals(get(ALT),"local company logo");
    }

    @Test
    @DisplayName("Test get element title")
    public void testTitle() {
        Target target = name("title");
        focus(target);
        assertEquals(get(TITLE),"title-locator");
    }

    @Test
    @DisplayName("Test get element style")
    public void testStyle() {
        Target target = tagWithText(H1,"Local Test Page");
        focus(target);
        assertEquals(get(STYLE),"color: red;");
    }

    @Test
    @DisplayName("Test get element href")
    public void testHref() {
        Target target = tagWithText(A,"Visit W3Schools");
        focus(target);
        assertEquals(get(HREF),"https://www.w3schools.com/");
    }

    @Test
    @DisplayName("Test get element placeholder")
    public void testPlaceholder() {
        Target target = id("identifier");
        focus(target);
        assertEquals(get(PLACEHOLDER),"type your id here");
    }

    @Test
    @DisplayName("Test get element lang")
    public void testLang() {
        origin();
        assertEquals(get(LANG),"en");
    }

}
