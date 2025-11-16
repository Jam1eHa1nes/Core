package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import support.TestContext;

public class Hooks {

    @Before
    public void selectEngine(Scenario scenario) {
        // Allow opting into specific engine via tags: @selenium or @playwright
        boolean wantsSelenium = scenario.getSourceTagNames().stream()
                .anyMatch(t -> t.equalsIgnoreCase("@selenium"));
        boolean wantsPlaywright = scenario.getSourceTagNames().stream()
                .anyMatch(t -> t.equalsIgnoreCase("@playwright"));

        if (wantsSelenium && wantsPlaywright) {
            throw new IllegalArgumentException("Scenario cannot be tagged with both @selenium and @playwright");
        }

        if (wantsSelenium) {
            System.setProperty("engine", "selenium");
        } else if (wantsPlaywright) {
            System.setProperty("engine", "playwright");
        }

        // Ensure a fresh driver per scenario; After hook also closes after execution
        TestContext.close();
    }

    @After
    public void tearDown() {
        TestContext.close();
    }
}
