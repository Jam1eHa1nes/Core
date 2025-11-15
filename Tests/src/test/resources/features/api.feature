Feature: Simple API checks with RestAssured

  Scenario: GET httpbin JSON and verify a field
    Given an API base URI of "https://httpbin.org"
    When I GET "/json"
    Then the response status should be 200
    And the JSON path "slideshow.author" should equal "Yours Truly"
