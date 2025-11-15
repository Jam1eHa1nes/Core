package steps;

import io.cucumber.java.After;
import support.TestContext;

public class Hooks {
    @After
    public void tearDown() {
        TestContext.close();
    }
}
