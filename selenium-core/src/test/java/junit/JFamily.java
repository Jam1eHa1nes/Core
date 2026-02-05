package junit;


import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.TimeoutException;

import static com.selenium.qa.automation.core.Enums.ElementTrait.ID;
import static com.selenium.qa.automation.core.Enums.ElementTrait.TAG;
import static com.selenium.qa.automation.core.Enums.Index.FIRST;
import static com.selenium.qa.automation.core.Enums.Index.SECOND;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JFamily extends CommonPageObject {

    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }
    ///////////////////////////////////////////
    // Children, Siblings & Leaf //
    ///////////////////////////////////////////

    @Test
    @DisplayName("Test children() when there are children")
    public void testChildren1() {
        Target target = id("outer1");
        focus(target);
        collect(children());
        choose(FIRST);
        assertEquals(7,size());
        collectionPresent();
        collectionHasSize(size());
        assertTrue(get(ID).equals("inner1"));
        focus(children());
        printFocused();
    }

    @Test
    @DisplayName("Test children() with origin() and collect")
    public void testChildren2() {
        origin();
        collect(children());
        assertEquals(2,size());
        choose(FIRST);
        assertTrue(get(TAG).equals("head"));
        choose(SECOND);
        assertTrue(get(TAG).equals("body"));
    }

    @Test
    @DisplayName("Test children() with focus")
    public void testChildren3() {
        focus(children());
        assertTrue(get(TAG).equals("html"));
    }

    @Test
    @DisplayName("Test children() with origin and descend")
    public void testChildren3_2() {
        origin().descend(children());
        assertTrue(get(TAG).equals("head"));
    }

    @Test
    @DisplayName("Test children() with descend")
    public void testChildren3_1() {
        focus(id("outer1"));
        descend(children());
        assertTrue(get(ID).equals("inner1"));
    }

    @Test
    @DisplayName("Test children() with no children with collect")
    public void testChildren4() {
        Target target = id("leaf0f7_1");
        focus(target);
        collect(children());
        assertEquals(0,size());// it returns 0
    }

    @Test
    @DisplayName("Test children() with no children with choose(FIRST)")
    public void testChildren5() {
        focus(2);
        Target target = id("leafOf7_1");
        assertThrows(TimeoutException.class, () -> focus(target).collect(children()).choose(FIRST));
    }

    @Test
    @DisplayName("Test siblings()")
    public void testSiblings1() {
        Target target = id("inner1");
        focus(target);
        collect(siblings());
        printCollection();
        assertEquals(6,size());
    }

    @Test
    @DisplayName("Test siblings()")
    public void testSiblings2() {
        Target target = id("inner6");
        focus(target);
        collect(siblings());
        assertEquals(6,size());
    }

    @Test
    @DisplayName("Test siblings() with origin and focus()")
    public void testSiblings3() {
        focus(2);
        origin();
        assertThrows(TimeoutException.class, () -> focus(siblings()));
    }

    @Test
    @DisplayName("Test siblings() with no sibling with collect()")
    public void testSiblings4() {
        Target target = id("outer3");
        focus(target);
        collect(siblings());
        assertEquals(0,size()); // it returns 0
    }

    @Test
    @DisplayName("Test siblings() with no sibling with choose(FIRST)")
    public void testSiblings5() {
        Target target = id("outer3");
        assertThrows(CPOException.class, () -> focus(target).collect(siblings()).choose(FIRST));
    }

    @Test
    @DisplayName("Test leaf()")
    public void testLeaf1() {
        Target target = id("top");
        focus(target);
        leaf();
        assertTrue(get(ID).equals("inner1"));
    }

    @Test
    @DisplayName("Test leaf() with origin and focus")
    public void testLeaf4() {
        origin();
        leaf();
        assertTrue(get(TAG).equals("link"));
        ascend(2);
        assertTrue(get(TAG).equals("html"));
    }

    @Test
    @DisplayName("Test leaf() when the current node is the innermost node")
    public void testLeaf5() {
        Target target = id("leaf0f7_1");
        focus(target).leaf();
        assertTrue(get(ID).equals("leaf0f7_1"));
    }
}

