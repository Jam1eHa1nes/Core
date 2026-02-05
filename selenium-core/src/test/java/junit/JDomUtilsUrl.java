package junit;


import com.selenium.qa.automation.core.CommonPageObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JDomUtilsUrl extends CommonPageObject {

    String path = System.getProperty("user.dir");

    // Open browser
    @BeforeAll
    public void setUp() {
        log("JUNIT Set up");
        open();
        go("https://www.google.co.uk/");
    }

    /////////
    // URL //
    /////////
    @Test
    @DisplayName("Test url equals")
    public void url1() {
        urlEquals("https://www.google.com/");
    }

    @Test
    @DisplayName("Test url contains")
    public void url2() {
        urlContains("oogle");
    }

    @Test
    @DisplayName("Test url starts with")
    public void url3() {
        urlContains("https:");
    }

    @Test
    @DisplayName("Test url ends with")
    public void url4() {
        urlContains(".com/");
    }
}



