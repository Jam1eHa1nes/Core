package com.selenium.qa.automation.core.http;

import com.core.qa.automation.common.logger.Logger;
import com.core.qa.automation.common.utils.Colours;
import com.selenium.qa.automation.core.CommonPage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import static io.restassured.RestAssured.given;


public class HttpServiceImpl implements HttpServiceInterface {
    private Logger logger = new Logger();
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String baseURI;
    @Getter
    @Setter
    private String password;

    CommonPage commonPage = CommonPage.getInstance();

    // Singleton
    private static HttpServiceImpl instance;

    public static synchronized HttpServiceImpl getInstance() {
        if (instance == null) {
            instance = new HttpServiceImpl();
        }
        return instance;
    }

    @Override
    public Response post(String endpoint, String filePath) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(filePath)
                .when().post()
                .then().extract().response();
    }

    @Override
    public Response postWithAuth(String endpoint, String filePath, String host ) {

        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(host)
                .basePath(endpoint)
                .body(filePath)
                .when().post()
                .then().extract().response();
    }
    @Override
    public Response post(String endpoint) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().post()
                .then().extract().response();
    }

    @Override
    public Response get(String endpoint) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
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
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().get(param)
                .then().extract().response();

    }

    @Override
    public Response get(String endpoint, String id, String value) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .queryParam(id, value)
                .when().get()
                .then().extract().response();
    }

    @Override
    public Response put(String endpoint) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
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
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().put(param)
                .then().extract().response();

    }

    @Override
    public Response put(String endpoint, String param, String filePath) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(filePath)
                .when().put(param)
                .then().extract().response();
    }

    @Override
    public Response put(String endpoint, String filePath) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .body(filePath)
                .when().put()
                .then().extract().response();
    }

    @Override
    public Response delete(String endpoint) {
        return given().contentType(ContentType.JSON)
                .auth().preemptive().basic(commonPage.retrieve("USERNAME"), commonPage.retrieve("PASSWORD"))
                .baseUri(baseURI)
                .basePath(endpoint)
                .when().delete()
                .then().extract().response();
    }

    public void log(String... args) {
        logger.log(args);
    }

    public void log(Colours colour, String text) {
        logger.log(colour, text);
    }

    public void warn(Colours color, String text) {
        logger.warn(text);
    }
}
