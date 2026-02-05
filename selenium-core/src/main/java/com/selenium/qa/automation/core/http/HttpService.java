package com.selenium.qa.automation.core.http;

import com.core.qa.automation.common.utils.Colours;
import io.restassured.response.Response;

public class HttpService {

    // Singletons
    private final HttpServiceImpl commonHttp = HttpServiceImpl.getInstance();

    public Response post(String endpoint, String filePath) {
        return commonHttp.post(endpoint, filePath);
    }

    public Response post(String endpoint) {
        return commonHttp.post(endpoint);
    }

    public Response get(String endpoint) {
        return commonHttp.get(endpoint);
    }

    public Response get(String endpoint, String... params) {
        return commonHttp.get(endpoint, params);
    }

    public Response put(String endpoint) {
        return commonHttp.put(endpoint);
    }

    public Response put(String endpoint, String... params) {
        return commonHttp.put(endpoint, params);
    }

    public Response put(String endpoint, String param, String filePath) {
        return commonHttp.put(endpoint, param, filePath);
    }

    public Response put(String endpoint, String filePath) {
        return commonHttp.put(endpoint, filePath);
    }

    public Response get(String endpoint, String id, String value) {
        return commonHttp.get(endpoint, id, value);
    }

    public Response delete(String endpoint) {
        return commonHttp.delete(endpoint);
    }

    public Response postWithAuth(String endpoint, String filePath, String host) {
        return commonHttp.postWithAuth(endpoint, filePath, host);
    }

    public String getUserName() {
        return commonHttp.getUserName();
    }

    public void setUserName(String userName) {
        commonHttp.setUserName(userName);
    }

    public String getBaseURI() {
        return commonHttp.getBaseURI();
    }

    public void setBaseURI(String baseURI) {
        commonHttp.setBaseURI(baseURI);
    }

    public String getPassword() {
        return commonHttp.getPassword();
    }

    public void setPassword(String password) {
        commonHttp.setPassword(password);
    }

    public void log(String ... args) {
        log(args);
    }

    public void log(Colours color, String text) {
        log(color, text);
    }

    public void warn(String text) {
        warn(text);
    }
}
