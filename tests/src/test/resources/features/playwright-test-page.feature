@playwright-test
Feature: Playwright Test Page
  As a user
  I want to interact with the Playwright test page
  So that I can verify the automation framework works correctly

  Scenario: Search on Playwright documentation page
    Given I open the Playwright documentation page
    When I enter "locator" into the search field
    Then I should see search results
