package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pageObject.ExamplePage;
import support.TestContext;

public class ExampleSteps {
    private final ExamplePage examplePage = new ExamplePage(TestContext.actions());

    @Given("I open the url {string}")
    public void i_open_the_url(String url) {
        examplePage.open(url);
    }

    @Then("I should see the heading {string}")
    public void i_should_see_the_heading(String expected) {
        String actual = examplePage.headingText();
        Assert.assertNotNull("Heading should not be null", actual);
        Assert.assertTrue("Expected heading to contain '" + expected + "' but was '" + actual + "'",
                actual.contains(expected));
    }

    @Given("I focus the element {string}")
    public void i_focus_the_element(String selector) {
        examplePage.focus(selector);
    }

    @Given("I type {string} into {string}")
    public void i_type_into(String text, String selector) {
        examplePage.type(selector, text);
    }

    @When("I click {string}")
    public void i_click(String selector) {
        examplePage.click(selector);
    }

    @Then("I should see text {string} in {string}")
    public void i_should_see_text_in(String expected, String selector) {
        String actual = examplePage.text(selector);
        Assert.assertNotNull("Element text should not be null", actual);
        Assert.assertTrue("Expected element text to contain '" + expected + "' but was '" + actual + "'",
                actual.contains(expected));
    }
}
