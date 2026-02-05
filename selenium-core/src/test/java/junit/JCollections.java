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

import java.util.ArrayList;
import java.util.List;

import static com.selenium.qa.automation.core.Enums.Tag.OPTION;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JCollections extends CommonPageObject {


    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }


    /////////////////
    // COLLECTIONS //
    /////////////////
    @Test
    @DisplayName("Test focus and collect options by target id")
    public void testCollect1() {
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        collectionHasSize(7);
        focus(target).printCollection();
        int sizeOfCollection = size();
        assertEquals(sizeOfCollection, 7);
        assertNotEquals(sizeOfCollection, 6);
    }

    @Test
    @DisplayName("Test focus and choose(index) options by target id")
    public void testChoose1() {
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        choose(0);
        matches("Monday");
        contains("ond");
        choose(1);
        matches("Tuesday");
        contains("ues");
        choose(2);
        matches("Wednesday");
        contains("W");
        choose(3);
        matches("Thursday");
        contains("ursd");
        choose(4);
        matches("Friday");
        contains("Fr");
        choose(5);
        matches("Saturday");
        contains("aturd");
        choose(6);
        matches("Sunday");
        contains("Sunday");
    }

    @Test
    @DisplayName("Test focus and choose(ordinal) options by target id")
    public void testChoose2() {
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        choose(FIRST);
        matches("Monday");
        contains("ond");
        choose(SECOND);
        matches("Tuesday");
        contains("ues");
        choose(Enums.Index.THIRD);
        matches("Wednesday");
        contains("W");
        choose(Enums.Index.FOURTH);
        matches("Thursday");
        contains("ursd");
        choose(Enums.Index.FIFTH);
        matches("Friday");
        contains("Fr");
        choose(SIXTH);
        matches("Saturday");
        contains("aturd");
        choose(Enums.Index.SEVENTH);
        matches("Sunday");
        contains("Sunday");
    }

    @Test
    @DisplayName("Test focus and choose(list index) options by target id")
    public void testChoose3() {
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        choose(Enums.ListIndex.FIRST);
        matches("Monday");
        contains("ond");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Monday");
        contains("ond");
        choose(Enums.ListIndex.NEXT);
        matches("Tuesday");
        contains("uesd");
        choose(Enums.ListIndex.LAST);
        matches("Sunday");
        contains("Sunday");
        choose(Enums.ListIndex.NEXT);
        matches("Sunday");
        contains("Sunday");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Saturday");
        contains("Saturda");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Friday");
        contains("rida");
    }

    @Test
    @DisplayName("Test focus and choose(list index countup) options by target id")
    public void testChoose4() {
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        assertEquals(size(), 7);
        choose(Enums.ListIndex.FIRST);
        matches("Monday");
        contains("ond");
        choose(Enums.ListIndex.NEXT);
        matches("Tuesday");
        contains("uesd");
        choose(Enums.ListIndex.NEXT);
        matches("Wednesday");
        contains("W");
        choose(Enums.ListIndex.NEXT);
        matches("Thursday");
        contains("ursd");
        choose(Enums.ListIndex.NEXT);
        matches("Friday");
        contains("Fr");
        choose(Enums.ListIndex.NEXT);
        matches("Saturday");
        contains("aturd");
        choose(Enums.ListIndex.NEXT);
        matches("Sunday");
        contains("Sunday");
    }

    @Test
    @DisplayName("Test focus and choose(list index countdown) options by target id")
    public void testChoose5() {
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        assertEquals(size(), 7);
        choose(Enums.ListIndex.LAST);
        matches("Sunday");
        contains("Sunday");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Saturday");
        contains("aturd");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Friday");
        contains("Fr");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Thursday");
        contains("ursd");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Wednesday");
        contains("W");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Tuesday");
        contains("uesd");
        choose(Enums.ListIndex.PREVIOUS);
        matches("Monday");
        contains("ond");
    }

    @Test
    @DisplayName("Test presence and absence of element text in element list")
    public void testPresence1() {
        Target target = id("days");
        String WEDNESDAY = "Wednesday";
        focus(target).click();
        collect(tag(OPTION));
        present(WEDNESDAY);
        absent("ANYDAY");
    }

    @Test
    @DisplayName("Test presence and absence of element list in element list")
    public void testPresence2() {
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        present(days);
        days.remove("Sunday");
        present(days);
        days.remove("Saturday");
        present(days);
        days.remove("Friday");
        present(days);
        days.remove("Thursday");
        present(days);
        days.remove("Wednesday");
        present(days);
        days.remove("Tuesday");
        present(days);
        days.remove("Monday");
        present(days);
        present(new ArrayList<String>());
    }

    @Test
    @DisplayName("Test presence and absence of element list in element list")
    public void testPresence3() {
        List<String> days = new ArrayList<>();
        days.add("Monda");
        days.add("Tues");
        days.add("Wednesdayz");
        days.add("Thursdays");
        days.add("Fridays");
        days.add("Saturdays");
        days.add("Sundays");
        Target target = id("days");
        focus(target).click();
        collect(tag(OPTION));
        absent(days);
        days.clear();
        days.add("Fryday");
        absent(days);
    }

    ///////////////////////////////////////////
    // Collections Evaluation //
    ///////////////////////////////////////////

    @ParameterizedTest
    @CsvSource({
            "LI, 4, true",
            "LI, 5, false"
    })
    @DisplayName("Test collection has size")
    public void testCollectionHasSize(Enums.Tag tag, int size, boolean expectedResult) {
        Target target = id("testChooseOverloads");
        if (expectedResult) {
            focus(target).collect(tag(tag)).collectionHasSize(size);
        } else {
            assertThrows(AssertionError.class, () -> focus(target).collect(tag(tag)).collectionHasSize(size));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "LI, 3, true",
            "LI, 4, false"
    })
    @DisplayName("Test collection larger than")
    public void testCollectionLargerThan(Enums.Tag tag, int size, boolean expectedResult) {
        Target target = id("testChooseOverloads");
        if (expectedResult) {
            focus(target).collect(tag(tag)).collectionLargerThan(size);
        } else {
            assertThrows(AssertionError.class, () -> focus(target).collect(tag(tag)).collectionLargerThan(size));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "LI, 5, true",
            "LI, 4, false"
    })
    @DisplayName("Test collection smaller than")
    public void testCollectionSmallerThan(Enums.Tag tag, int size, boolean expectedResult) {
        Target target = id("testChooseOverloads");
        if (expectedResult) {
            focus(target).collect(tag(tag)).collectionSmallerThan(size);
        } else {
            assertThrows(AssertionError.class, () -> focus(target).collect(tag(tag)).collectionSmallerThan(size));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "LI, true",
            "MAP, false"
    })
    @DisplayName("Test collection present")
    public void testCollectionPresent(Enums.Tag tag, boolean expectedResult) {
        Target target = id("testChooseOverloads");
        if (expectedResult) {
            focus(target).collect(tag(tag)).collectionPresent();
        } else {
            assertThrows(AssertionError.class, () -> focus(target).collect(tag(tag)).collectionPresent());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "HEADER, true",
            "LI, false"
    })
    @DisplayName("Test collection not present")
    public void testCollectionNotPresent(Enums.Tag tag, boolean expectedResult) {
        Target target = id("testChooseOverloads");
        if (expectedResult) {
            focus(target).collect(tag(tag)).collectionNotPresent();
        } else {
            assertThrows(AssertionError.class, () -> focus(target).collect(tag(tag)).collectionNotPresent());
        }
    }
}

