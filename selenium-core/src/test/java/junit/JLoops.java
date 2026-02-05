package junit;


import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.selenium.qa.automation.core.Enums.Tag.OPTION;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JLoops extends CommonPageObject {


    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }

    // LOOPS

    @Test
    @DisplayName("Test a click loop")
    public void testLoop1() {
        collect(className("checkboxes")).loop().click().endLoop();

        focus(id("checkbox1")).selected();
        focus(id("checkbox2")).selected();
        focus(id("checkbox3")).selected();

        refresh();
    }

    /*@Test
    @DisplayName("Test a loop that composes using a list")
    public void testLoopComposition() {
        Iterator<String> text = List.of("some", "looped", "text").iterator();
        origin().collect(className("texts")).loop().compose(text::next).endLoop();

        assert focus(id("text1")).get(VALUE).equals("some");
        assert focus(id("text2")).get(VALUE).equals("looped");
        assert focus(id("text3")).get(VALUE).equals("text");

        refresh();
    }*/

    /*@Test
    @DisplayName("Test looping with a descend")
    public void testLoop2() {
        Iterator<String> text = List.of("some", "looped", "text").iterator();
        origin().collect(className("input-subcontainers")).loop().descend().compose(text::next).endLoop();

        assertEquals("some",    focus(id("text1")).get(VALUE));
        assertEquals("looped",  focus(id("text2")).get(VALUE));
        assertEquals("text",    focus(id("text3")).get(VALUE));

        refresh();
    }*/

    @Test
    @DisplayName("Test looping with contain")
    public void testLoop3() {
        focus(id("days")).collect(tag(OPTION));
        loop().contains("day").endLoop();
    }

    @Test
    @DisplayName("Test looping with enable")
    public void testLoop4() {
        focus(id("countries")).collect(tag(OPTION));
        loop().enabled().endLoop();
    }

    @Test
    @DisplayName("Test looping with contain with a thrown exception")
    public void testLoop5() {
        assertThrows(CPOException.class, () -> focus(id("countries")).collect(tag(OPTION)).loop().contains("A").endLoop());
    }
}

