package junit;


import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.Enums;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.selenium.qa.automation.core.Enums.ElementTrait.ID;
import static com.selenium.qa.automation.core.Enums.ElementTrait.TAG;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JDirection extends CommonPageObject {

    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }

    /////////////////////
    //TEST RELATIVES  //
    /////////////////////

    @Test
    @DisplayName("Test focus and ascend()")
    public void testRel1() {
        Target target = id("inner1");
        focus(target);
        ascend();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("outer1"));
    }

    @Test
    @DisplayName("Test focus and ascend(1)")
    public void testRel2() {
        Target target = id("inner1");
        focus(target);
        ascend(1);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("outer1"));
    }

    @Test
    @DisplayName("Test focus and ascend(2)")
    public void testRel3() {
        Target target = id("inner1");
        focus(target);
        ascend(2);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("outer2"));
    }

    @Test
    @DisplayName("Test focus and ascend(3)")
    public void testRel4() {
        Target target = id("inner1");
        focus(target);
        ascend(3);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("outer3"));
    }

    @Test
    @DisplayName("Test focus and ascend(Tag)")
    public void testAscendTag1() {
        Target target = id("cognate_5");
        focus(target);
        ascend(SECTION);
        assertTrue(get(ID).equals("cognate_1"));
    }

    @Test
    @DisplayName("Test when focus and ascend(Tag) are the same element")
    public void testAscendTag2() {
        Target target = id("cognate_5");
        focus(target);
        ascend(DIV);
        assertTrue(get(ID).equals("cognate_5"));
    }

    @Test
    @DisplayName("Test focus and non-existing ascend(Tag)")
    public void testAscendTag3() {
        Target target = id("cognate_5");
        focus(target);
        assertThrows(CPOException.class, () -> ascend(H5));
    }

    @Test
    @DisplayName("Test focus and non-parent ascend(Tag)")
    public void testAscendTag4() {
        Target target = id("cognate_5");
        focus(target);
        assertThrows(CPOException.class, () -> ascend(H1));
    }

    @Test
    @DisplayName("Test focus and descend()")
    public void testRel5() {
        Target target = id("outer3");
        focus(target);
        descend();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("outer2"));
    }

    @Test
    @DisplayName("Test focus and descend() two times")
    public void testRel6() {
        Target target = id("top");
        focus(target);
        descend();
        descend();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("outer2"));
    }

    @Test
    @DisplayName("Test focus and reverse")
    public void testRel7() {
        Target target = id("inner3");
        focus(target);
        reverse();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner2"));
    }

    @Test
    @DisplayName("Test focus and reverse() two times")
    public void testRel8() {
        Target target = id("inner3");
        focus(target);
        reverse();
        reverse();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner1"));
    }

    @Test
    @DisplayName("Test focus and traverse()")
    public void testRel9() {
        Target target = id("inner1");
        focus(target);
        traverse();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner2"));
    }

    @Test
    @DisplayName("Test focus and traverse() two times")
    public void testRel10() {
        Target target = id("inner1");
        focus(target);
        traverse();
        traverse();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner3"));
    }

    @Test
    @DisplayName("Test focus and descend by target id")
    public void testRel11() {
        Target target = id("top");
        focus(target);
        descend(id("inner1"));
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner1"));
    }

    @Test
    @DisplayName("Test focus and descend by target id")
    public void testRel12() {
        Target target = id("top");
        focus(target);
        descend(id("inner1"));
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner1"));
    }

    @Test
    @DisplayName("Test traverse()")
    public void testTraverse1() {
        Target target = id("inner1");
        focus(target);
        traverse();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner2"));
    }

    @Test
    @DisplayName("Test traverse(1)")
    public void testTraverse2() {
        Target target = id("inner1");
        focus(target);
        traverse(1);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner2"));
    }

    @Test
    @DisplayName("Test traverse(2)")
    public void testTraverse4() {
        Target target = id("inner1");
        focus(target);
        traverse(2);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner3"));
    }

    @Test
    @DisplayName("Test traverse(6)")
    public void testTraverse5() {
        Target target = id("inner1");
        focus(target);
        traverse(6);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner7"));
    }

    @Test
    @DisplayName("Test traverse(FIRST)")
    public void testTraverse3() {
        Target target = id("inner1");
        focus(target);
        traverse(Enums.NodeEnum.FIRST);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner2"));
    }

    @Test
    @DisplayName("Test traverse(SECOND)")
    public void testTraverse6() {
        Target target = id("inner1");
        focus(target);
        traverse(Enums.NodeEnum.SECOND);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner3"));
    }

    @Test
    @DisplayName("Test traverse(FOURTH)")
    public void testTraverse10() {
        Target target = id("inner1");
        focus(target);
        traverse(Enums.NodeEnum.FOURTH);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner5"));
    }

    @Test
    @DisplayName("Test traverse(0)")
    public void testTraverse7() {
        Target target = id("inner1");
        focus(target);
        assertThrows(CPOException.class, () -> traverse(0));
    }

    @Test
    @DisplayName("Test traverse(7) where there is no following sibling")
    public void testTraverse8() {
        Target target = id("inner1");
        focus(target);
        assertThrows(CPOException.class, () -> traverse(7));
    }

    @Test
    @DisplayName("Test traverse() where there is no following sibling")
    public void testTraverse9() {
        Target target = id("inner7");
        focus(target);
        assertThrows(CPOException.class, () -> traverse());
    }

    @Test
    @DisplayName("Test traverse(-1)")
    public void testTraverse91() {
        Target target = id("inner4");
        focus(target);
        assertThrows(CPOException.class, () -> traverse(-1));
    }


    @Test
    @DisplayName("Test reverse()")
    public void testReverse1() {
        Target target = id("inner7");
        focus(target);
        reverse();
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner6"));
    }

    @Test
    @DisplayName("Test reverse(1)")
    public void testReverse2() {
        Target target = id("inner7");
        focus(target);
        reverse(1);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner6"));
    }

    @Test
    @DisplayName("Test reverse(FIRST)")
    public void testReverse3() {
        Target target = id("inner7");
        focus(target);
        reverse(Enums.NodeEnum.FIRST);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner6"));
    }

    @Test
    @DisplayName("Test reverse(THIRD)")
    public void testReverse31() {
        Target target = id("inner7");
        focus(target);
        reverse(Enums.NodeEnum.THIRD);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner4"));
    }

    @Test
    @DisplayName("Test reverse(3)")
    public void testReverse4() {
        Target target = id("inner7");
        focus(target);
        reverse(3);
        assertTrue(get(TAG).equals("div"));
        assertTrue(get(ID).equals("inner4"));
    }

    @Test
    @DisplayName("Test reverse(0)")
    public void testReverse5() {
        Target target = id("inner6");
        focus(target);
        assertThrows(CPOException.class, () -> reverse(0));
    }

    @Test
    @DisplayName("Test reverse() where there is no previous sibling")
    public void testReverse6() {
        Target target = id("inner1");
        focus(target);
        assertThrows(CPOException.class, () -> reverse());
    }

    @Test
    @DisplayName("Test reverse(9) where there is no previous sibling")
    public void testReverse7() {
        Target target = id("inner7");
        focus(target);
        assertThrows(CPOException.class, () -> reverse(9));
    }

    @Test
    @DisplayName("Test traverse(-1)")
    public void testReverse8() {
        Target target = id("inner4");
        focus(target);
        assertThrows(CPOException.class, () -> reverse(-1));
    }
}

