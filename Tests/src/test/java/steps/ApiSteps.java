package steps;

import core.api.ApiClient;
import core.api.impl.RestAssuredApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

/**
 * Basic API step definitions using RestAssured so API tests can live alongside UI tests.
 */
public class ApiSteps {
    private final ApiClient api = new RestAssuredApiClient();
    private Response response;

    @Given("an API base URI of {string}")
    public void an_api_base_uri_of(String baseUri) {
        api.setBaseUri(baseUri);
    }

    @When("I GET {string}")
    public void i_get(String path) {
        response = api.get(path);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        Assert.assertNotNull("Response should not be null", response);
        Assert.assertEquals("Unexpected HTTP status", expectedStatus, response.statusCode());
    }

    @Then("the JSON path {string} should equal {string}")
    public void the_json_path_should_equal(String jsonPath, String expected) {
        Assert.assertNotNull("Response should not be null", response);
        String actual = response.jsonPath().getString(jsonPath);
        Assert.assertEquals("Unexpected JSON value at path: " + jsonPath, expected, actual);
    }
}
