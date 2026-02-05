Feature: Test Core BDD Hierarchy

  Background:
    Given I open the CHROME Browser

  Scenario: Search Bing
    When I navigate to and search the following Engines
      | searchEngine | searchCriterea |
      | BING         | java island    |
      | DUCKDUCK     | kotlin island  |
      | YANDEX       | lombok island  |

  Scenario: I log in as user
    And I go to "https://qa.trunarrative.cloud"
    And I login as user
    And I navigate to the Audit Trail

