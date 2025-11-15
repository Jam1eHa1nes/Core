package core.api;

import io.restassured.response.Response;

import java.util.Map;

/**
 * A lightweight API client interface to simplify common HTTP actions for tests.
 * Implementations may use RestAssured (default) or any other HTTP client.
 */
public interface ApiClient {
    /** Set the base URI used for all requests, e.g. https://api.example.com */
    void setBaseUri(String baseUri);

    // GET
    Response get(String path);
    Response get(String path, Map<String, ?> queryParams);
    Response get(String path, Map<String, ?> queryParams, Map<String, String> headers);

    // POST
    Response post(String path);
    Response post(String path, Object body);
    Response post(String path, Map<String, ?> queryParams, Object body);
    Response post(String path, Map<String, ?> queryParams, Map<String, String> headers, Object body);

    // PUT
    Response put(String path);
    Response put(String path, Object body);
    Response put(String path, Map<String, ?> queryParams, Object body);
    Response put(String path, Map<String, ?> queryParams, Map<String, String> headers, Object body);

    // PATCH
    Response patch(String path);
    Response patch(String path, Object body);
    Response patch(String path, Map<String, ?> queryParams, Object body);
    Response patch(String path, Map<String, ?> queryParams, Map<String, String> headers, Object body);

    // DELETE
    Response delete(String path);
    Response delete(String path, Map<String, ?> queryParams);
    Response delete(String path, Map<String, ?> queryParams, Map<String, String> headers);
}
