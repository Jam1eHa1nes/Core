package com.core.qa.automation.common.http;

import io.restassured.response.Response;

/**
 * Interface for HTTP service operations using REST Assured.
 * Provides methods for common HTTP operations (GET, POST, PUT, DELETE).
 */
public interface HttpServiceInterface {

    /**
     * Performs a POST request with a JSON body.
     *
     * @param endpoint the API endpoint
     * @param body     the request body
     * @return the response
     */
    Response post(String endpoint, String body);

    /**
     * Performs a POST request with authentication to a specific host.
     *
     * @param endpoint the API endpoint
     * @param body     the request body
     * @param host     the host URL
     * @return the response
     */
    Response postWithAuth(String endpoint, String body, String host);

    /**
     * Performs a POST request without a body.
     *
     * @param endpoint the API endpoint
     * @return the response
     */
    Response post(String endpoint);

    /**
     * Performs a GET request.
     *
     * @param endpoint the API endpoint
     * @return the response
     */
    Response get(String endpoint);

    /**
     * Performs a GET request with query parameters.
     *
     * @param endpoint the API endpoint
     * @param params   the query parameters
     * @return the response
     */
    Response get(String endpoint, String... params);

    /**
     * Performs a GET request with a specific query parameter.
     *
     * @param endpoint the API endpoint
     * @param id       the parameter name
     * @param value    the parameter value
     * @return the response
     */
    Response get(String endpoint, String id, String value);

    /**
     * Performs a PUT request without a body.
     *
     * @param endpoint the API endpoint
     * @return the response
     */
    Response put(String endpoint);

    /**
     * Performs a PUT request with query parameters.
     *
     * @param endpoint the API endpoint
     * @param params   the query parameters
     * @return the response
     */
    Response put(String endpoint, String... params);

    /**
     * Performs a PUT request with a body and query parameter.
     *
     * @param endpoint the API endpoint
     * @param param    the query parameter
     * @param body     the request body
     * @return the response
     */
    Response put(String endpoint, String param, String body);

    /**
     * Performs a PUT request with a body.
     *
     * @param endpoint the API endpoint
     * @param body     the request body
     * @return the response
     */
    Response putWithBody(String endpoint, String body);

    /**
     * Performs a PATCH request with a body.
     *
     * @param endpoint the API endpoint
     * @param body     the request body
     * @return the response
     */
    Response patch(String endpoint, String body);

    /**
     * Performs a DELETE request.
     *
     * @param endpoint the API endpoint
     * @return the response
     */
    Response delete(String endpoint);

    /**
     * Performs a DELETE request with a body.
     *
     * @param endpoint the API endpoint
     * @param body     the request body
     * @return the response
     */
    Response delete(String endpoint, String body);
}

