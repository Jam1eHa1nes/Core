package core.api.impl;

import core.api.ApiClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Collections;
import java.util.Map;

/**
 * Default ApiClient implementation backed by RestAssured.
 */
public class RestAssuredApiClient implements ApiClient {
    @Override
    public void setBaseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
    }

    // region GET
    @Override
    public Response get(String path) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .when().get(path)
                .then().extract().response();
    }

    @Override
    public Response get(String path, Map<String, ?> queryParams) {
        return prepare(queryParams, Collections.emptyMap())
                .when().get(path)
                .then().extract().response();
    }

    @Override
    public Response get(String path, Map<String, ?> queryParams, Map<String, String> headers) {
        return prepare(queryParams, headers)
                .when().get(path)
                .then().extract().response();
    }
    // endregion

    // region POST
    @Override
    public Response post(String path) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .when().post(path)
                .then().extract().response();
    }

    @Override
    public Response post(String path, Object body) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(path)
                .then().extract().response();
    }

    @Override
    public Response post(String path, Map<String, ?> queryParams, Object body) {
        return prepare(queryParams, Collections.emptyMap())
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(path)
                .then().extract().response();
    }

    @Override
    public Response post(String path, Map<String, ?> queryParams, Map<String, String> headers, Object body) {
        return prepare(queryParams, headers)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(path)
                .then().extract().response();
    }
    // endregion

    // region PUT
    @Override
    public Response put(String path) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .when().put(path)
                .then().extract().response();
    }

    @Override
    public Response put(String path, Object body) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .contentType(ContentType.JSON)
                .body(body)
                .when().put(path)
                .then().extract().response();
    }

    @Override
    public Response put(String path, Map<String, ?> queryParams, Object body) {
        return prepare(queryParams, Collections.emptyMap())
                .contentType(ContentType.JSON)
                .body(body)
                .when().put(path)
                .then().extract().response();
    }

    @Override
    public Response put(String path, Map<String, ?> queryParams, Map<String, String> headers, Object body) {
        return prepare(queryParams, headers)
                .contentType(ContentType.JSON)
                .body(body)
                .when().put(path)
                .then().extract().response();
    }
    // endregion

    // region PATCH
    @Override
    public Response patch(String path) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .when().patch(path)
                .then().extract().response();
    }

    @Override
    public Response patch(String path, Object body) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .contentType(ContentType.JSON)
                .body(body)
                .when().patch(path)
                .then().extract().response();
    }

    @Override
    public Response patch(String path, Map<String, ?> queryParams, Object body) {
        return prepare(queryParams, Collections.emptyMap())
                .contentType(ContentType.JSON)
                .body(body)
                .when().patch(path)
                .then().extract().response();
    }

    @Override
    public Response patch(String path, Map<String, ?> queryParams, Map<String, String> headers, Object body) {
        return prepare(queryParams, headers)
                .contentType(ContentType.JSON)
                .body(body)
                .when().patch(path)
                .then().extract().response();
    }
    // endregion

    // region DELETE
    @Override
    public Response delete(String path) {
        return prepare(Collections.emptyMap(), Collections.emptyMap())
                .when().delete(path)
                .then().extract().response();
    }

    @Override
    public Response delete(String path, Map<String, ?> queryParams) {
        return prepare(queryParams, Collections.emptyMap())
                .when().delete(path)
                .then().extract().response();
    }

    @Override
    public Response delete(String path, Map<String, ?> queryParams, Map<String, String> headers) {
        return prepare(queryParams, headers)
                .when().delete(path)
                .then().extract().response();
    }
    // endregion

    private RequestSpecification prepare(Map<String, ?> queryParams, Map<String, String> headers) {
        RequestSpecification spec = RestAssured.given().relaxedHTTPSValidation();
        if (queryParams != null && !queryParams.isEmpty()) {
            spec = spec.queryParams(queryParams);
        }
        if (headers != null && !headers.isEmpty()) {
            spec = spec.headers(headers);
        }
        return spec;
    }
}
