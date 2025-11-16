Feature: Example site smoke

  @playwright
  Scenario: Playwright parity scenario
    Given I open the url "https://the-internet.herokuapp.com/login"
    And I focus the element "#username"
    And I type "tomsmith" into "#username"
    And I type "SuperSecretPassword!" into "#password"
    When I click "button[type=submit]"
    Then I should see text "Secure Area" in "h2"

  @selenium
  Scenario: Selenium parity scenario
    Given I open the url "https://the-internet.herokuapp.com/login"
    And I focus the element "#username"
    And I type "tomsmith" into "#username"
    And I type "SuperSecretPassword!" into "#password"
    When I click "button[type=submit]"
    Then I should see text "Secure Area" in "h2"
