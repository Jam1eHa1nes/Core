package junit;


import com.core.qa.automation.common.utils.PropertiesReader;
import com.selenium.qa.automation.core.CPOException;
import com.selenium.qa.automation.core.CommonPageObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.selenium.qa.automation.core.Enums.ElementTrait.TEXT;
import static com.selenium.qa.automation.core.VMArgs.browserKeepOpen;
import static junit.TestUtils.setUpJUnitTests;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JProperties extends CommonPageObject {


    // Open browser
    @BeforeAll
    public void setUp() {
        setUpJUnitTests(this);
    }


    ////////////////
    // PROPERTIES //
    ////////////////

    @Test
    @DisplayName("Existing properties")
    public void testProperties1() {
        assertEquals(PropertiesReader.get("test.prop.boolean"), "true");
        assertEquals(PropertiesReader.get("test.prop.int"), "99");
        assertEquals(PropertiesReader.get("test.prop.string"), "text");
    }

    @Test
    @DisplayName("Property defined but no value")
    public void testProperties2() {
        assertTrue(PropertiesReader.get("test.prop.null").isEmpty());
    }

    @Test
    @DisplayName("Property does not exist")
    public void testProperties3() {
        assertTrue(PropertiesReader.get("test.xml.prop.does.not.exist").isEmpty());
    }

    @Test
    @DisplayName("Property key is null")
    public void testProperties4() {
        assertThrows(IllegalArgumentException.class, () -> PropertiesReader.get(null));
    }

    @Test
    @DisplayName("Mandatory Text")
    public void mandatoryText() {
        String actual = focus(id("mandatoryText")).get(1).get(TEXT);
        assertEquals( actual, "Mandatory Text" );
        assertThrows(CPOException.class, () -> focus(id("noText")).get(TEXT));
    }

    @Test
    @DisplayName("Property key is empty")
    public void mandatoryText1() {
        assertThrows(IllegalArgumentException.class, () -> PropertiesReader.get(""));
    }


    @Test
    @DisplayName("Property key is empty")
    public void testProperties5() {
        assertThrows(IllegalArgumentException.class, () -> PropertiesReader.get(""));
    }


    @Test
    @DisplayName("browserKeepOpen will default to false")
    public void testVMArgs1() {
        assertFalse(browserKeepOpen);
    }

    @Test
    @DisplayName("Check that the JSExecutor is working.")
    public void testJSExe() {
        String jsVal = javascript("return document.getElementById('att-eval-id').value");
        assertEquals("att-eval-value", jsVal);
    }

// Set Runtime V2VMArgs and browser.keep.open=false..Default
//    @Test
//    @DisplayName("browserKeepOpen false")
//    public void testVMArgs2() {
//        assertFalse(browserKeepOpen);
//        assertEquals(Properties.assertTrue(get("browser.keep.open"), "false");
//    }

// Set Runtime V2VMArgs and browser.keep.open=true
//    @Test
//    @DisplayName("browserKeepOpen true")
//    public void testVMArgs3() {
//        assertTrue(browserKeepOpen);
//        assertEquals(Properties.assertTrue(get("browser.keep.open"), "true");
//    }


}

