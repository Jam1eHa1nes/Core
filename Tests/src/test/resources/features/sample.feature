Feature: Example site smoke

  Scenario: Open example.com and verify heading
    Given I open the url "https://example.com"
    Then I should see the heading "Example Domain"

  Scenario: Use all UI actions on a demo login page
    Given I open the url "https://the-internet.herokuapp.com/login"
    And I focus the element "#username"
    And I type "tomsmith" into "#username"
    And I type "SuperSecretPassword!" into "#password"
    When I click "button[type=submit]"
    Then I should see text "Secure Area" in "h2"
