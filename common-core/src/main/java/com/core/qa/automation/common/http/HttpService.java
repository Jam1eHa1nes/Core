package com.core.qa.automation.common.http;

import com.core.qa.automation.common.StorageProvider;
import com.core.qa.automation.common.logger.Logger;
import com.core.qa.automation.common.utils.Colours;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import static io.restassured.RestAssured.given;

/**
 * Implementation of HttpServiceInterface providing REST Assured HTTP operations.
 * Can work independently or integrate with a StorageProvider for credentials.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     HttpService httpService = HttpService.getInstance();
 *     httpService.setBaseURI("https://api.example.com");
 *     httpService.setUserName("user");
 *     httpService.setPassword("pass");
 *     Response response = httpService.get("/users");
 * </pre>
 */
public class HttpService implements HttpServiceInterface {

    private final Logger logger = new Logger();

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String baseURI;

    @Getter
    @Setter
    private String password;

    @Setter
    private StorageProvider storageProvider;

    private static HttpService instance;

    public static synchronized HttpService getInstance() {
        if (instance == null) {
            instance = new HttpService();
        }
        return instance;
    }

    /**
     * Gets the username, either from direct property or from storage provider.
     */
    private String getEffectiveUserName() {
        if (userName != null && !userName.isEmpty()) {
            return userName;
        }
        if (storageProvider != null) {
            return storageProvider.retrieve("USERNAME");
        }
        return null;
    }

    /**
     * Gets the password, either from direct property or from storage provider.
     */
    private String getEffectivePassword() {
        if (password != null && !password.isEmpty()) {
            return password;
        }
        if (storageProvider != null) {
            return storageProvider.retrieve("PASSWORD");
        }
        return null;
    }

    @Override
    public Response post(String endpoint, String body) {
        log("POST", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(body)
                .when().post()
                .then().extract().response();
    }

    @Override
    public Response postWithAuth(String endpoint, String body, String host) {
        log("POST (Auth)", endpoint + " -> " + host);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(host)
                .basePath(endpoint)
                .body(body)
                .when().post()
                .then().extract().response();
    }

    @Override
    public Response post(String endpoint) {
        log("POST", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().post()
                .then().extract().response();
    }

    @Override
    public Response get(String endpoint) {
        log("GET", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().get()
                .then().extract().response();
    }

    @Override
    public Response get(String endpoint, String... params) {
        String param = "";
        if (params.length == 1) {
            param = "?" + params[0];
        }
        if (params.length > 1) {
            param = "?" + params[0] + "&" + params[1];
        }
        log("GET", endpoint + param);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().get(param)
                .then().extract().response();
    }

    @Override
    public Response get(String endpoint, String id, String value) {
        log("GET", endpoint + "?" + id + "=" + value);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .queryParam(id, value)
                .when().get()
                .then().extract().response();
    }

    @Override
    public Response put(String endpoint) {
        log("PUT", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().put()
                .then().extract().response();
    }

    @Override
    public Response put(String endpoint, String... params) {
        String param = "";
        if (params.length == 1) {
            param = "?" + params[0];
        }
        if (params.length > 1) {
            param = "?" + params[0] + "&" + params[1];
        }
        log("PUT", endpoint + param);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().put(param)
                .then().extract().response();
    }

    @Override
    public Response put(String endpoint, String param, String body) {
        log("PUT", endpoint + "?" + param);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(body)
                .when().put(param)
                .then().extract().response();
    }

    @Override
    public Response putWithBody(String endpoint, String body) {
        log("PUT", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(body)
                .when().put()
                .then().extract().response();
    }

    @Override
    public Response patch(String endpoint, String body) {
        log("PATCH", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(body)
                .when().patch()
                .then().extract().response();
    }

    @Override
    public Response delete(String endpoint) {
        log("DELETE", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().delete()
                .then().extract().response();
    }

    @Override
    public Response delete(String endpoint, String body) {
        log("DELETE", endpoint);
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(getEffectiveUserName(), getEffectivePassword())
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(body)
                .when().delete()
                .then().extract().response();
    }

    public void log(String... args) {
        logger.log(args);
    }

    public void log(Colours colour, String text) {
        logger.log(colour, text);
    }

    public void warn(String text) {
        logger.warn(text);
    }
}

