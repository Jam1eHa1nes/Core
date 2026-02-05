package com.selenium.qa.automation.core.http;

import io.restassured.response.Response;

public interface HttpServiceInterface {

    Response post(String endpoint, String filePath);

    Response postWithAuth(String endpoint, String filePath, String host);

    Response post(String endpoint);

    Response get(String endpoint);

    Response get(String endpoint, String... params);

    Response put(String endpoint);

    Response put(String endpoint, String... params);

    Response put(String endpoint, String param, String filePath);

    Response put(String endpoint, String filePath);

    Response get(String endpoint, String id, String value);

    Response delete(String endpoint);
}
