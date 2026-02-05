package junit;

import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Enums;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.TimeoutException;

import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JCognate extends CommonPageObject {

    @BeforeAll
    void setUp() {
        setUpJUnitTests(this);
    }

    @Test
    void shouldFindCognateWhenTargetIsDescendant() {

        // given
        Target origin = id("outer3");
        Target nearestRelative = id("inner1");
        String expected = "Inner1";

        // when
        cognate(origin, nearestRelative);

        // then
        assertEquals(expected, get(Enums.ElementTrait.TEXT));
    }

    @Test
    void shouldFindCognateWhenoriginIsOrigin() {

        // given
        Target origin = tag(Enums.Tag.HTML);
        Target nearestRelative = id("inner1");
        String expected = "Inner1";

        // when
        cognate(origin, nearestRelative);

        // then
        assertEquals(expected, get(Enums.ElementTrait.TEXT));
    }

    @Test
    void shouldFindFirstElementWhenMultipleMatchTarget() {

        // given
        Target origin = id("country");
        Target nearestRelative = tag(Enums.Tag.OPTION);
        String expected = "Afghanistan";

        // when
        cognate(origin, nearestRelative);

        // then
        assertEquals(expected, get(Enums.ElementTrait.VALUE));
    }

    @Test
    void shouldFindElementWithOriginSharedParent() {

        // given
        Target origin = id("country");
        Target nearestRelative = id("inner1");
        String expected = "Inner1";

        // when
        cognate(origin, nearestRelative);

        // then
        assertEquals(expected, get(Enums.ElementTrait.TEXT));
    }

    @Test
    void shouldThrowErrorWhennearestRelativeNotFound() {

        // given
        Target origin = id("country");
        Target nearestRelative = id("not a valid id");

        // then
        assertThrows(CPOException.class, () -> cognate(origin, nearestRelative));
    }

    @Test
    void shouldFindNearestElementToorigin() {

        // given
        Target origin = id("outer2");
        Target nearestRelative = tagWithText(Enums.Tag.P, "Cognate test");
        String expected = "p-child";

        // when
        cognate(origin, nearestRelative);

        // then
        assertEquals(expected, get(Enums.ElementTrait.ID));
    }

    @Test
    void shouldFindElementWhenTargetIsParent() {

        // given
        Target origin = id("outer1");
        Target nearestRelative = tagWithText(Enums.Tag.DIV, "Outer3");
        String expected = "outer3";

        // when
        cognate(origin, nearestRelative);

        // then
        assertEquals(expected, get(Enums.ElementTrait.ID));
    }

    @Test
    @DisplayName("Test where the source and destination are the same element")
    public void testCognate1() {
        Target origin = id("top");
        Target nearestRelative = tagWithId(DIV, "top");
        cognate(origin, nearestRelative);
        assertEquals("top",get(Enums.ElementTrait.ID));
    }

    @Test
    @DisplayName("Test where the source and destination are the same element - HTML")
    public void testCognate1_1() {
        Target origin = tag(HTML);
        Target nearestRelative = tag(HTML);
        assertThrows(CPOException.class, () -> cognate(origin, nearestRelative));
    }

    @Test
    @DisplayName("Test where the source is the HTML and the relative is not exist")
    public void testCognate2() {
        Target origin = tag(HTML);
        Target nearestRelative = tagWithText(DIV, "Le123e");
        assertThrows(CPOException.class, () -> cognate(origin, nearestRelative));
    }

    @Test
    @DisplayName("Test where the source is the HTML and the relative is not a direct child")
    public void testCognate2_1() {
        Target origin = tag(HTML);
        Target nearestRelative = id( "cognate_5");
        cognate(origin, nearestRelative);
        assertEquals("cognate_5",get(Enums.ElementTrait.ID));
    }

    @Test
    @DisplayName("Test where the source is the innermost node and the relative is the HTML")
    public void testCognate2_2() {
        Target origin = id("cognate_5");
        Target nearestRelative = tag(HTML);
        assertThrows(CPOException.class, () -> cognate(origin, nearestRelative));
    }

    @Test
    @DisplayName("Test the nearestRelative is the innermost node in the document from the origin")
    public void testCognate3() {
        Target origin = id("cognate");
        Target nearestRelative = tagWithText(DIV, "cognate_5");
        cognate(origin, nearestRelative);
        assertEquals("cognate_5",get(Enums.ElementTrait.ID));
    }

    @Test
    @DisplayName("Test the origin is the innermost node in the document")
    public void testCognate4() {
        Target origin = id("cognate_5");
        Target nearestRelative = tagWithText(P, "Cognate part 2");
        cognate(origin, nearestRelative);
        assertEquals("cognate",get(Enums.ElementTrait.ID));
    }

    @Test
    @DisplayName("Test the origin does not exist")
    public void testCognate5() {
        cognate(2);
        Target origin = id("cognate_5xxx");
        Target nearestRelative = tagWithText(DIV, "Cognate part 2");
        assertThrows(TimeoutException.class, () -> cognate(origin, nearestRelative));
    }

    @Test
    @DisplayName("Test the relatives are found both below and above the origin")
    public void testCognate6() {
        //The nearestRelative should be a child
        Target origin = id("cognate_2");
        Target nearestRelative = tagWithTitle(DIV, "cognate_2_relative");
        cognate(origin, nearestRelative);
        assertEquals("cognate_3",get(Enums.ElementTrait.ID));
    }

    @Test
    @DisplayName("Test multiple Relatives are found below the origin.")
    public void testCognate7() {
        //The nearestRelative should be the first child
        Target origin = id("outer1");
        Target nearestRelative = tagWithName(DIV, "inner family");
        cognate(origin, nearestRelative);
        assertEquals("inner1",get(Enums.ElementTrait.ID));
    }

    @Test
    @DisplayName("Test next relatives are found with single param after setting current element.")
    public void testCognate8(){
        Target origin = id("outer3");
        Target nearestRelative = id("inner1");
        String expected = "Inner1";
        focus(origin);
        cognate(nearestRelative);
        assertEquals(expected, get(Enums.ElementTrait.TEXT));
    }

    @Test
    @DisplayName("Test next relatives are not found with single param if current element is related")
    public void testCognate9() {
        Target origin = id("cognate_2");
        Target nearestRelative = id("not a valid id");
        focus(origin);
        assertThrows(CPOException.class, () -> cognate(nearestRelative));
    }

    @Test
    @DisplayName("Test single param works with custom wait timeout")
    public void testCognate10() {
        Target origin = id("outer3");
        Target nearestRelative = id("inner1");
        String expected = "Inner1";
        cognate(2);
        focus(origin);
        cognate(nearestRelative);
        assertEquals(expected, get(Enums.ElementTrait.TEXT));
    }

    @Test
    @DisplayName("Test single param can be chained")
    public void testCognate11() {
        Target origin = id("outer3");
        Target nearestRelative = id("inner1");
        String expected = "Inner1";
        focus(origin).cognate(nearestRelative);
        assertEquals(expected, get(Enums.ElementTrait.TEXT));
    }

    @Test
    @DisplayName("Test cognate chaining")
    public void testCognate13() {
        Target origin = tagWithText("label","Admin User");
        Target nearestRelative = tagWithText("label","Email");
        Target nearestRelative2 = tag("input");
        String expected = "organisation-name";
        cognate(origin, nearestRelative).cognate(nearestRelative2);
        assertEquals(expected, get(Enums.ElementTrait.NAME));
    }

    @Test
    @DisplayName("Test cognate chaining element not found")
    public void testCognate14() {
        cognate(2);
        Target origin = tagWithText("label","Admin User");
        Target nearestRelative = tagWithText("label","Email");
        Target nearestRelative2 = tagWithText(Enums.Tag.LABEL, "Does not exist");
        assertThrows(CPOException.class, () -> cognate(origin, nearestRelative).cognate(nearestRelative2));
    }

}
