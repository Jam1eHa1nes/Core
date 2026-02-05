package com.core.qa.automation.tests.steps;

import com.core.qa.automation.tests.po.PlaywrightPagePO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Playwright documentation page.
 * Delegates to PlaywrightPagePO for all page interactions.
 */
public class PlaywrightPageSteps {

    private final PlaywrightPagePO playwrightPagePO;

    public PlaywrightPageSteps() {
        this.playwrightPagePO = new PlaywrightPagePO();
    }

    @Given("I open the Playwright documentation page")
    public void iOpenThePlaywrightDocumentationPage() {
        playwrightPagePO.openPlaywrightDocsPage();
    }

    @When("I enter {string} into the search field")
    public void iEnterIntoTheSearchField(String searchText) {
        playwrightPagePO.search(searchText);
    }

    @When("I click on the {word} navigation link")
    public void iClickOnTheNavigationLink(String linkText) {
        playwrightPagePO.clickNavLink(linkText);
    }

    @Then("I should see search results")
    public void iShouldSeeSearchResults() {
        assertThat(playwrightPagePO.areSearchResultsDisplayed())
            .as("Search results should be displayed")
            .isTrue();
    }

    @Then("I should see search results containing {string}")
    public void iShouldSeeSearchResultsContaining(String expectedText) {
        assertThat(playwrightPagePO.searchResultsContainText(expectedText))
            .as("Search results should contain: " + expectedText)
            .isTrue();
    }
}
