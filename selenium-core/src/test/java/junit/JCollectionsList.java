package junit;


import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import com.selenium.qa.automation.core.locators.Target;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static com.selenium.qa.automation.core.Enums.ListIndex.LAST;
import static com.selenium.qa.automation.core.Enums.ListIndex.NEXT;
import static com.selenium.qa.automation.core.Enums.Tag.LABEL;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JCollectionsList extends CommonPageObject {


    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }


    /////////////////
    // COLLECTIONS //
    /////////////////
    @Test
    @DisplayName("Test targets are present")
    public void testCollect1() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        assertTrue(collect(targetList).size()==3);
    }

    @Test
    @DisplayName("Test targets are present but wrong size")
    public void testCollect1_3() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        assertFalse(collect(targetList).size()==4);
    }

    @Test
    @DisplayName("Test three targets but one target is not present")
    public void testCollect1_1() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"fakeDolphin"));
        targetList.add(placeholder("please type here"));
        assertThrows(CPOException.class, () -> collect(targetList));
    }

    @Test
    @DisplayName("Test all target are not present")
    public void testCollect1_2() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1000"));
        targetList.add(tagWithText(LABEL,"fakeDolphin"));
        targetList.add(placeholder("please type there"));
        assertThrows(CPOException.class, () -> collect(targetList));
    }

    @Test
    @DisplayName("Test targets are clickable")
    public void testCollect2() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(placeholder("please type here"));
        targetList.add(id("checkbox2"));
        targetList.add(name("checkbox 3"));
        collect(targetList);
        choose(FIRST).click();
        choose(SECOND).click();
        choose(THIRD).click();
    }

    @Test
    @DisplayName("Test targets are writeable")
    public void testCollect3() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("text1"));
        targetList.add(placeholder("input here please"));
        targetList.add(name("input 3"));
        collect(targetList);
        choose(FIRST).compose("FIRST");
        choose(SECOND).compose("SECOND");
        choose(THIRD).compose("THIRD");
        assertTrue(choose(FIRST).get(VALUE).equals("FIRST"));
        assertTrue(choose(SECOND).get(VALUE).equals("SECOND"));
        assertTrue(choose(THIRD).get(VALUE).equals("THIRD"));
    }

    @Test
    @DisplayName("Test targets selected by FIRST,SECOND,THIRD and locators checked")
    public void testCollect4() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        collect(targetList);
        assertTrue(choose(FIRST).get(ID).equals("checkbox1"));
        assertTrue(choose(SECOND).get(TEXT).equals("Dolphin"));
        assertTrue(choose(THIRD).get(PLACEHOLDER).equals("please type here"));
    }

    @Test
    @DisplayName("Test targets selected by FIRST,NEXT,LAST and locators checked")
    public void testCollect5() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        collect(targetList);
        assertTrue(choose(FIRST).get(ID).equals("checkbox1"));
        assertTrue(choose(NEXT).get(TEXT).equals("Dolphin"));
        assertTrue(choose(LAST).get(PLACEHOLDER).equals("please type here"));
    }

    @Test
    @DisplayName("Test targets reset")
    public void testCollect6() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        assertTrue(collect(targetList).reset().size()==0);
    }

    ////////////////////////
    /////// Domutils ///////
    ////////////////////////

    @Test
    @DisplayName("Test targets with Domutils-collectionPresent")
    public void testCollect7() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        collect(targetList).collectionPresent();
    }


    @Test
    @DisplayName("Test targets with Domutils-collectionNotPresent")
    public void testCollect11() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        collect(targetList).reset();
        collectionNotPresent();
    }

    @Test
    @DisplayName("Test targets with Domutils-collectionHasSize")
    public void testCollect8() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        collect(targetList);
        collectionHasSize(3);
    }

    @Test
    @DisplayName("Test targets with Domutils-collectionLargerThan")
    public void testCollect9() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        collect(targetList);
        collectionLargerThan(2);
    }

    @Test
    @DisplayName("Test targets with Domutils-collectionSmallerThan")
    public void testCollect10() {
        focus(2);
        List<Target> targetList = new ArrayList<>();
        targetList.add(id("checkbox1"));
        targetList.add(tagWithText(LABEL,"Dolphin"));
        targetList.add(placeholder("please type here"));
        collect(targetList);
        collectionSmallerThan(4);
    }
}
