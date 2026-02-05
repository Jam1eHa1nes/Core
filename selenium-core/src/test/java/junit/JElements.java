package junit;


import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Enums;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;

import static com.selenium.qa.automation.core.Enums.ElementTrait.*;
import static com.selenium.qa.automation.core.Enums.ElementTrait.TITLE;
import static com.selenium.qa.automation.core.Enums.Tag.*;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JElements extends CommonPageObject {
    
    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }

    @Test
    @DisplayName("Test focus by Target and Id")
    public void testId2() {
        Target target = id("identifier");
        focus(target).compose("hello again");
    }

    @Test
    @DisplayName("Test focusing by text Target on element with leading and trailing spaces")
    public void testFocusingOnLeadingAndTrailingSpacesWithTextTargetWithNoCompensatorySpaces() {
        focus(text("Some text with leading and trailing spaces"));
        assertTrue(get(ID).equals("spaces"));
        focus(partialText(" Some text with leading and trailing spaces "));
        assertTrue(get(ID).equals("spaces"));
    }

    @Test
    @DisplayName("Test Choose With Partial Text")
    public void testChooseWithPartialText() {
        focus(id("testChooseOverloads")).collect(tag(LI));
        collectionHasSize(4);
        printCollection();
        assertThrows(CPOException.class, () -> choose("Some"));
        assertThrows(Exception.class, () -> choose("Some", true));
        choose("Some",false);
        choose("Some\nText");
        choose("Some\nMore\nText");
        choose("Exact Text With Spaces");
        choose("Exact Text With Spaces", true);
        choose("Exact Text With Spaces", false);
        choose(" Exact Text With Spaces ", true);
        choose(" Exact Text With Spaces ", false);
        choose(" More ", false);
        choose("More", false);
        assertThrows(CPOException.class, () -> choose("More"));
        assertThrows(CPOException.class, () -> choose("More", true));
    }

    @Test
    @DisplayName("Test focus by Target and Tag Name")
    public void testTagName2() {
        Target target = tag(H1);
        focus(target).matches("Lexis Nexis Test Page").contains("Test");
    }

    @Test
    @DisplayName("Test focus by Target and Css")
    public void testCss2() {
        Target target = attribute("csstest", "csstestlocator");
        focus(target).compose("hello from css");
    }

    @Test
    @DisplayName("Test compose with no type specified")
    public void testCompose1() {
        Target target = id("no-type-specified");
        assertDoesNotThrow(() -> focus(target).compose("hello hello"));
    }

    @Test
    @DisplayName("Test compose with type specified")
    public void testCompose2() {
        Target target = id("type-specified");
        assertDoesNotThrow(() -> focus(target).compose("hello hello"));
    }

    @Test
    @DisplayName("Test compose textarea with no type specified")
    public void testCompose3() {
        Target target = id("textarea-no-type-specified");
        assertDoesNotThrow(() -> focus(target).compose("hello hello"));
    }

    @Test
    @DisplayName("Test compose textarea with type specified")
    public void testCompose4() {
        Target target = id("textarea-with-type-specified");
        assertDoesNotThrow(() -> focus(target).compose("hello hello"));
    }

    @Test
    @DisplayName("Test compose with no compose field")
    public void testCompose5() {
        Target target = tagWithText(H1,"Local Test Page");
        CPOException exception = assertThrows(CPOException.class, () ->  focus(target).compose("hello hello"));
        assertEquals("Element has no type attribute or is not editable", exception.getMessage());
    }

    @Test
    @DisplayName("Test focus by Target and attribute 1")
    public void testAttribute1() {
        Target target = attribute("enabled");
        focus(target);
        assertTrue(get(ID).equals("top"));
    }

    @Test
    @DisplayName("Test focus by Target and attribute 2")
    public void testAttribute2() {
        Target target = id("days");
        focus(target);
        collect(attribute("disabled"));
        collectionHasSize(3);
    }

    @Test
    @DisplayName("Test focus by Target and print 1")
    public void testPrint1() {
        Target target = id("days");
        focus(target);
        printFocused();
        collect(attribute("disabled"));
        collectionHasSize(3);
        printCollection();
    }

    @Test
    @DisplayName("Test focus by Target and print 2")
    public void testPrint2() {
        Target target = id("div2");
        focus(target);
        printFocused();
    }

    @Test
    @DisplayName("Test focus by Target and Class Name")
    public void testClassName2() {
        Target target = className("class1");
        focus(target).compose("hello from class name");
    }

    @Test
    @DisplayName("Test focus by Target and class names 1")
    public void testClassNames1() {
        Target target = classNames("class2");
        focus(target);
        assertTrue(get(ID).equals("class-names"));
    }

    @Test
    @DisplayName("Test focus by Target and class names 2")
    public void testClassNames2() {
        Target target = classNames("class2 class3");
        focus(target);
        assertTrue(get(ID).equals("class-names"));
    }

    @Test
    @DisplayName("Test focus by Target and Name")
    public void testName2() {
        Target target = name("name-test-locator");
        focus(target).compose("hello from name");
    }

    @Test
    @DisplayName("Test focus by Target and Value")
    public void testValue2() {
        Target target = value("value-test-locator");
        focus(target).compose("hello from value");

    }

    @Test
    @DisplayName("Test focus by Target and Text")
    public void testText2() {
        Target target = text("Dolphin");
        focus(target).matches("Dolphin");
    }


    @Test
    @DisplayName("Test focus by Target and partialText")
    public void testPartialText2() {
        Target target = partialText("lph");
        focus(target).matches("Dolphin");
    }

    @Test
    @DisplayName("Test focus by Target and tagWithText")
    public void testTagWithText2() {
        Target target = tagWithText(H1, "Local Test Page");
        focus(target).matches("Local Test Page");
    }

    @Test
    @DisplayName("Test focus by Target and tagWithId")
    public void testTagWithId() {
        Target target = tagWithId(DIV,"inner1");
        focus(target).matches("Inner1");
    }

    @Test
    @DisplayName("Test focus by Target and tagWithClass")
    public void testTagWithClass() {
        Target target = tagWithClass(INPUT,"class1");
        focus(target);
        assertTrue(get(CLASS).equals("class1"));
    }

    @Test
    @DisplayName("Test focus by Target and tagWithTitle")
    public void testTagWithTitle() {
        Target target = tagWithTitle(INPUT,"title-locator");
        focus(target);
        assertTrue(get(TITLE).equals("title-locator"));
    }

    @Test
    @DisplayName("Test focus by Target and tagWithName")
    public void testTagWithName() {
        Target target = tagWithName(INPUT,"name-test-locator");
        focus(target);
        assertTrue(get(TAG).equals("input"));
        assertTrue(get(NAME).equals("name-test-locator"));
    }

    @Test
    @DisplayName("Test focus by Target and placeholder1")
    public void testPlaceholder1() {
        Target target = placeholder("type your id here");
        focus(target);
        assertTrue(get(ID).equals("identifier"));
    }

    @Test
    @DisplayName("Test focus by Target and placeholder2")
    public void testPlaceholder2() {
        Target target = placeholder("type some text here");
        focus(target);
        assertTrue(get(ID).equals("textarea"));
    }

    @Test
    @DisplayName("Test focus by Target and linkText")
    public void testLinkText2() {
        Target target = linkText("link text");
        focus(target).click();
    }

    @Test
    @DisplayName("Test focus by Target and partial linkText")
    public void testPartialLinkText2() {
        Target target = partialLinkText("link");
        focus(target).click();
    }

    @Test
    @DisplayName("Test the enabled method")
    public void testEnabled() {
        Target SELECT_DAYS = id("days");
        Target OPTION_TUESDAY = value("tuesday");
        focus(SELECT_DAYS).descend(OPTION_TUESDAY).enabled();
    }

    @Test
    @DisplayName("Test the disabled method")
    public void testDisabled() {
        Target SELECT_DAYS = id("days");
        Target OPTION_MONDAY = value("monday");
        focus(SELECT_DAYS).descend(OPTION_MONDAY).disabled();
    }

    @Test
    @DisplayName("Test the selected method")
    public void testSelected() {
        Target SELECT_DAYS = id("days");
        Target OPTION_TUESDAY = value("tuesday");
        focus(SELECT_DAYS).click().descend(OPTION_TUESDAY).click().selected();
        printFocused();
    }


    @Test
    @DisplayName("Test that click waits for clickability, without needing a specific focus")
    public void testClickTestsClickability() {
        Target button = id("disabled-button");
        focus(button).click().click();
    }

    @Test
    @DisplayName("Test that click eventually times out")
    public void testClickTimeout() {
        Target button = id("permanently-disabled-button");
        assertThrows(CPOException.class, () -> focus(button).click());
    }

    // Test additional targets
    @Test
    @DisplayName("Test focus by Target and title")
    public void testTitle() {
        Target target = title("title-locator");
        focus(target);
        assertTrue(get(TITLE).equals("title-locator"));
    }

    @Test
    @DisplayName("Test focus by Target and href")
    public void testHref() {
        Target target = href("#");
        focus(target);
        assertTrue(get(TAG).equals("a"));
    }

    @Test
    @DisplayName("Test focus by Target and src")
    public void testSrc() {
        Target target = src("./img/Logo.jpg");
        focus(target);
        assertTrue(get(ID).equals("logo"));
    }

    @Test
    @DisplayName("Test focus by Target and style")
    public void testStyle() {
        Target target = style("color:red");
        focus(target);
        assertTrue(get(TAG).equals("h1"));
    }

    @Test
    @DisplayName("Test focus by Target and alt")
    public void testAlt() {
        Target target = alt("local company logo");
        focus(target);
        assertTrue(get(ID).equals("logo"));
    }


    ///////////////////////////////////////////
    // Attribute Evaluation //
    ///////////////////////////////////////////

    @ParameterizedTest
    @CsvSource({
            "TITLE, att-eval-title, att-eval-title, true",
            "TITLE, att-eval-title, wrong-title, false",
            "VALUE, att-eval-value, att-eval-value, true",
            "VALUE, att-eval-value, wrong-value, false"
    })
    @DisplayName("Test attributeEquals")
    public void testAttributeEquals(
            Enums.ElementTrait attribute,
            String expectedValue,
            String actualValue,
            boolean expectedResult)
    {
        Target target = id("att-eval-id");
        focus(target).attributeEquals(attribute, expectedValue);
        assertEquals(expectedResult, get(attribute).equals(actualValue));
    }

    @ParameterizedTest
    @CsvSource({
            "TITLE, att-eval-title, t-eval-tit, true",
            "TITLE, att-eval-title, additional-att-eval-title-additional, false",
            "VALUE, att-eval-value, val, true",
            "VALUE, att-eval-value, additional-att-eval-value-additional, false"
    })
    @DisplayName("Test attributeContains")
    public void testAttributeContains(Enums.ElementTrait attribute,
                                      String expectedValue,
                                      String actualValue,
                                      boolean expectedResult)
    {
        Target target = id("att-eval-id");
        focus(target).attributeContains(attribute, expectedValue);
        assertEquals(expectedResult, get(attribute).contains(actualValue));
    }

    @ParameterizedTest
    @CsvSource({
            "TITLE, att-eval-title, att-e, true",
            "TITLE, att-eval-title, tt-eval-title, false",
            "VALUE, att-eval-value, a, true",
            "VALUE, att-eval-value, tt-eval-value, false"
    })
    @DisplayName("Test attributeStartsWith")
    public void testAttributeStartsWith(
            Enums.ElementTrait attribute,
            String expectedValue,
            String actualValue,
            boolean expectedResult)
    {
        Target target = id("att-eval-id");
        focus(target).attributeStartsWith(attribute, expectedValue);
        assertEquals(expectedResult, get(attribute).startsWith(actualValue));
    }

    @ParameterizedTest
    @CsvSource({
            "TITLE, att-eval-title, tle, true",
            "TITLE, att-eval-title, att-eval-titl, false",
            "VALUE, att-eval-value, tt-eval-value, true",
            "VALUE, att-eval-value, att-eval, false"
    })
    @DisplayName("Test attributeEndsWith")
    public void testAttributeEndsWith(
            Enums.ElementTrait attribute,
            String expectedValue,
            String actualValue,
            boolean expectedResult)
    {
        Target target = id("att-eval-id");
        focus(target).attributeEndsWith(attribute, expectedValue);
        assertEquals(expectedResult, get(attribute).endsWith(actualValue));
    }

    @Test
    @DisplayName("Test compose with password field")
    public void testPassword() {
        Target target = id("password");
        focus(target).compose("85jfj920kd!@");
    }

    @Test
    @DisplayName("Test focus with two targets first one exists")
    public void testFocusTwoTargets1() {
        focus(2);
        focus(id("checkbox1"), id("doesNotExist")).click();
    }

    @Test
    @DisplayName("Test focus with two targets second one exists")
    public void testFocusTwoTargets2() {
        focus(2);
        focus(id("doesNotExist"), id("checkbox2")).click();
    }

    @Test
    @DisplayName("Test focus with two targets neither exist")
    public void testFocusTwoTargets3() {
        focus(2);
        assertThrows(CPOException.class, () -> focus(id("doesNotExist"), id("thisNeither")));
    }

    @Test
    @DisplayName("Test focus works as normal")
    public void testFocusTwoTargets4() {
        focus(2);
        focus(id("checkbox1"), id("doesNotExist")).click();
        focus(id("doesNotExist"), id("checkbox2")).click();
        focus(id("checkbox1")).click();
        focus(id("checkbox2")).click();
        assertThrows(TimeoutException.class, () -> focus(id("doesNotExist")));
    }

    @Test
    @DisplayName("Test focus with multiple targets")
    public void testFocusMultipleTargets1() {
        focus(id("checkbox1"), id("checkbox2"),id("checkbox3")).click();
    }

    @Test
    @DisplayName("Test focus with multiple targets one does not exist")
    public void testFocusMultipleTargets2() {
        focus(id("checkbox1"), id("checkbox2"),id("doesNotExist")).click();
    }

    @Test
    @DisplayName("Test focus with multiple targets two do not exist")
    public void testFocusMultipleTargets3() {
        focus(id("checkbox1"), id("doesNotExist2"),id("doesNotExist3")).click();
    }

    @Test
    @DisplayName("Test focus with multiple targets, none of them exist")
    public void testFocusMultipleTargets4() {
        assertThrows(CPOException.class, () -> focus(id("doesNotExist1"), id("doesNotExist2"),id("doesNotExist3")).click());
    }

    @Test
    @DisplayName("Test focus with multiple targets with custom timeout")
    public void testFocusMultipleTargets5() {
        focus(5,id("checkbox1"), id("checkbox2"),id("checkbox3")).click();
    }

    @Test
    @DisplayName("Test hold & release by copy and paste")
    public void testCopyAndPaste() {
        Target target1 = id("identifier");
        focus(target1).compose(" Copy and Paste").click();
        copy();
        Target target2 = id("att-eval-id");
        focus(target2).click();
        paste();
        assertTrue(get(VALUE).endsWith("Copy and Paste"));
        focus(target2).hold(Keys.CONTROL)
                .hold("a")
                .release("a")
                .release(Keys.CONTROL)
                .hold(Keys.DELETE);
        focus(target2).compose("att-eval-value");
    }

    @Test
    @DisplayName("Test focus with exception - focus(1) no exception")
    public void testFocusWaitTime1() {
        focus(1);
    }

    @Test
    @DisplayName("Test focus with exception - focus(0) with exception")
    public void testFocusWaitTime2() {
        assertThrows(CPOException.class, () -> focus(0));
    }

    @Test
    @DisplayName("Test focus with exception - focus(-1) with exception")
    public void testFocusWaitTime3() {
        assertThrows(CPOException.class, () -> focus(-1));
    }

    @Test
    @DisplayName("Test peek with existing element")
    public void testPeek(){
        Target target = tagWithClass(INPUT,"class1");
        Target nonExisting = tagWithClass(INPUT,"non-existing");
        assertTrue(peek(target));
        assertFalse(peek(1).peek(nonExisting));
    }
}

