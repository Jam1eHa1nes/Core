package junit;


import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.locators.TargetFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.selenium.qa.automation.core.Enums.ElementTrait.NAME;
import static com.selenium.qa.automation.core.Enums.ElementTrait.VALUE;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JDomUtils extends CommonPageObject {

    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }
    ///////////
    // TITLE //
    ///////////

    @Test
    @DisplayName("Test title equals")
    public void title1() {
        titleEquals("Local Test Page");
    }

    @Test
    @DisplayName("Test title equals with a thrown exception")
    public void title1_1() {
        assertThrows(AssertionError.class, () -> titleEquals("Hello world"));
    }

    @Test
    @DisplayName("Test title contains")
    public void title2() {
        titleContains("is N");
    }

    @Test
    @DisplayName("Test title contains with a thrown exception")
    public void title2_1() {
        assertThrows(AssertionError.class, () -> titleContains("Hello world"));
    }

    @Test
    @DisplayName("Test title starts with")
    public void title3() {
        titleStartsWith("L");
    }

    @Test
    @DisplayName("Test title starts with with a thrown exception")
    public void title3_1() {
        assertThrows(AssertionError.class, () -> titleStartsWith("LEX"));
    }

    @Test
    @DisplayName("Test title ends with")
    public void title4() {
        titleEndsWith("ge");
    }

    @Test
    @DisplayName("Test title ends with with a thrown exception")
    public void title4_1() {
        assertThrows(AssertionError.class, () -> titleEndsWith("E"));
    }



    //////////////////
    // ELEMENT TEXT //
    //////////////////

    @Test
    @DisplayName("Test text equals")
    public void text1() {
        focus(id("spaces"));
        textEquals("Some text with leading and trailing spaces");
    }

    @Test
    @DisplayName("Test text equals with a thrown exception")
    public void text2() {
        focus(id("spaces"));
        assertThrows(AssertionError.class, () -> textEquals("Some text with No leading and trailing spaces"));
    }

    @Test
    @DisplayName("Test text differs")
    public void text3() {
        focus(id("spaces"));
        textDiffers("Some text with No leading and trailing spaces");
    }

    @Test
    @DisplayName("Test text differs with a thrown exception")
    public void text4() {
        focus(id("spaces"));
        assertThrows(AssertionError.class, () -> textDiffers("Some text with leading and trailing spaces"));
    }

    @Test
    @DisplayName("Test text differs")
    public void text5() {
        focus(id("spaces"));
        textDiffers("ling spaces");
    }

    @Test
    @DisplayName("Test text contains with a thrown exception")
    public void text6() {
        focus(id("spaces"));
        assertThrows(AssertionError.class, () -> textContains("hello world"));
    }

    @Test
    @DisplayName("Test text starts with")
    public void text7() {
        focus(id("spaces"));
        textStartsWith("S");
    }

    @Test
    @DisplayName("Test text starts with with a thrown exception")
    public void text8() {
        focus(id("spaces"));
        assertThrows(AssertionError.class, () -> textStartsWith("text"));
    }

    @Test
    @DisplayName("Test text present")
    public void text9() {
        focus(id("spaces"));
        printFocused();
        textPresent();
    }

    @Test
    @DisplayName("Test text present with a thrown exception")
    public void text10() {
        focus(id("textarea"));
        assertThrows(CPOException.class, () -> textPresent());

    }

    @Test
    @DisplayName("Test text present with parameter")
    public void text11() {
        focus(id("spaces"));
        textPresent("Some text with leading and trailing spaces");
    }

    @Test
    @DisplayName("Test text present with parameter with a thrown exception")
    public void text12() {
        focus(id("spaces"));
        assertThrows(AssertionError.class, () -> textPresent("Some text with ling and trailing spaces"));
    }

    //TODO CP throws exception

//    @Test
//    @DisplayName("Test textNotPresent")
//    public void text13() {
//        focus(id("noText"));
//        textNotPresent();
//    }
//
//    @Test
//    @DisplayName("Test textNotPresent")
//    public void text14() {
//        focus(id("noText"));
//        assertThrows(CPOException.class, () -> textNotPresent());
//
//    }
    //    @Test
//    @DisplayName("Test textNotPresent with parameter")
//    public void text13() {
//        focus(id("noText"));
//        textNotPresent("xxx");
//    }
//
//    @Test
//    @DisplayName("Test textNotPresent with parameter")
//    public void text14() {
//        focus(id("noText"));
//        assertThrows(CPOException.class, () -> textNotPresent("xxx"));
//
//    }

    @Test
    @DisplayName("Test text has length")
    public void textHasLength1() {
        focus(value("monday"));
        textHasLength(6);
    }

    @Test
    @DisplayName("Test text has length with a thrown exception")
    public void textHasLength2() {
        focus(value("monday"));
        assertThrows(AssertionError.class, () -> textHasLength(2));
    }



    ////////////////
    // ATTRIBUTES //
    ////////////////

    @Test
    @DisplayName("Test attribute equals")
    public void attribute1() {
        focus(id("country"));
        attributeEquals(NAME, "country");
    }

    @Test
    @DisplayName("Test attribute equals")
    public void attribute2() {
        focus(value("monday"));
        attributeEquals(VALUE, "monday");
    }

    @Test
    @DisplayName("Test attribute equals with a thrown exception")
    public void attribut3() {
        focus(value("tuesday"));
        assertThrows(CPOException.class, () -> attributeEquals(NAME,"tuesday"));
    }

    @Test
    @DisplayName("Test attribute equals with a thrown exception")
    public void attribut3_1() {
        focus(value("tuesday"));
        assertThrows(AssertionError.class, () -> attributeEquals(VALUE,"monday"));
    }

    @Test
    @DisplayName("Test attribute contains")
    public void attribute4() {
        focus(value("monday"));
        attributeContains(VALUE, "mon");
    }

    @Test
    @DisplayName("Test attribute contains with a thrown exception")
    public void attribut5() {
        focus(value("tuesday"));
        assertThrows(AssertionError.class, () -> attributeContains(VALUE,"happy"));
    }

    //////////////
    // ELEMENTS //
    //////////////

    @Test
    @DisplayName("Test element present")
    public void element1() {
        elementPresent(TargetFactory.tag(OPTION));
    }

    @Test
    @DisplayName("Test element present")
    public void element2() {
        elementPresent(tagWithText(OPTION,"Monday"));
    }

    @Test
    @DisplayName("Test element present with a thrown exception")
    public void element3() {
        assertThrows(AssertionError.class, () -> elementPresent(tagWithText(OPTION,"Non existent element")));
    }

    @Test
    @DisplayName("Test element not present")
    public void element4() {
        elementNotPresent(tagWithText(OPTION,"Non existent element"));
    }

    @Test
    @DisplayName("Test element not present with a thrown exception")
    public void element5() {
        assertThrows(AssertionError.class, () -> elementNotPresent(tagWithText(OPTION,"Monday")));
    }

    /////////////////
    // COLLECTIONS //
    /////////////////

    @Test
    @DisplayName("Test collection present")
    public void collection1() {
        focus(id("days"));
        click();
        collect(tag(OPTION));
        collectionPresent();
    }

    @Test
    @DisplayName("Test collection present with a thrown exception")
    public void collection2() {
        focus(id("top"));
        click();
        collect(tag(OPTION));
        assertThrows(AssertionError.class, () -> collectionPresent());
    }

    @Test
    @DisplayName("Test collection not present")
    public void collection3() {
        focus(id("top"));
        click();
        collect(tag(OPTION));
        collectionNotPresent();
    }

    @Test
    @DisplayName("Test collection not present with a thrown exception")
    public void collection4() {
        focus(id("days"));
        click();
        collect(tag(OPTION));
        assertThrows(AssertionError.class, () -> collectionNotPresent());
    }

    @Test
    @DisplayName("Test collection has size")
    public void collection5() {
        focus(id("days"));
        click();
        collect(tag(OPTION));
        collectionHasSize(7);
    }

    @Test
    @DisplayName("Test collection has size with a thrown exception")
    public void collection6() {
        focus(id("days"));
        click();
        collect(tag(OPTION));
        assertThrows(AssertionError.class, () -> collectionHasSize(6));
    }


    @Test
    @DisplayName("Test collection larger than")
    public void collection7() {
        focus(id("outer1"));
        click();
        collect(children());
        collectionHasSize(7);
        collectionLargerThan(6);
    }

    @Test
    @DisplayName("Test collection larger than with a thrown exception")
    public void collection8() {
        focus(id("outer1"));
        click();
        collect(children());
        assertThrows(AssertionError.class, () -> collectionLargerThan(7));
    }

    @Test
    @DisplayName("Test collection smaller than")
    public void collection9() {
        focus(id("leaf0f7_1"));
        click();
        collect(siblings());
        collectionHasSize(1);
        collectionSmallerThan(2);
    }

    @Test
    @DisplayName("Test collection smaller than with a thrown exception")
    public void collection10() {
        focus(id("leaf0f7_1"));
        click();
        collect(siblings());
        assertThrows(AssertionError.class, () -> collectionSmallerThan(1));
    }


    /////////
    // TAG //
    /////////

    @Test
    @DisplayName("Test tag is")
    public void tag1() {
        focus(id("leaf0f7_1"));
        printFocused();
        tagIs(DIV);
    }

    @Test
    @DisplayName("Test tag is")
    public void tag2() {
        focus(id("mandatoryText"));
        printFocused();
        tagIs(P);
    }

    @Test
    @DisplayName("Test tag is with a thrown exception")
    public void tag3() {
        focus(id("mandatoryText"));
        printFocused();
        assertThrows(AssertionError.class, () -> tagIs(DIV));
    }

    @Test
    @DisplayName("Test tag is not")
    public void tag4() {
        focus(id("leaf0f7_1"));
        printFocused();
        tagIsNot(P);
    }

    @Test
    @DisplayName("Test tag is not with a thrown exception")
    public void tag5() {
        focus(id("mandatoryText"));
        printFocused();
        assertThrows(AssertionError.class, () -> tagIs(DIV));
    }
}



