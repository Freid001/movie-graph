package com.movies;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class StepDefinitions
{
    private static final String CONTENT_TYPE = "application/json";

    private HashMap<String, Object> attributes = new HashMap<>();
    private ValidatableResponse then;
    private String body;

    @Before
    public void before() {
        Helper.configureRestAssured();

        this.attributes = new HashMap<>();
        this.body = null;
    }

    @After
    public void after() {
        Helper.resetNeo4j();
    }

    @Given("the request body is:")
    public void the_request_body_is(String body) {
        this.body = body;
    }

    @When("I request {string} using HTTP GET")
    public void i_request_using_HTTP_GET(String uri) {
        then = given()
                .contentType(CONTENT_TYPE)
                .when()
                .get(Helper.replacePlaceholders(attributes, uri))
                .then();
    }

    @When("I request {string} using HTTP POST")
    public void i_request_using_HTTP_POST(String uri) {
        then = given()
                .contentType(CONTENT_TYPE)
                .body(this.body)
                .when()
                .post(Helper.replacePlaceholders(attributes, uri))
                .then();
    }

    @When("I request {string} using HTTP DELETE")
    public void i_request_using_HTTP_DELETE(String uri) {
        then = given()
                .contentType(CONTENT_TYPE)
                .when()
                .delete(Helper.replacePlaceholders(attributes, uri))
                .then();
    }

    @Then("extract {string} as placeholder {string}")
    public void extract_as_placeholder(String path, String as) {
        attributes.put(as, then.extract().path(path).toString());
    }

    @Then("the response code is {int}")
    public void the_response_code_is(int status) {
        then.statusCode(status);
    }

    @Then("the response body {string} = {string}")
    public void response_body_equals_string(String path, String expected) {
        if(then.extract().path(path) != null){
            assertEquals(Helper.replacePlaceholders(attributes, expected), StringUtils.defaultString(then.extract().path(path).toString()));
        }else {
            assertEquals(Helper.replacePlaceholders(attributes, expected), "");
        }
    }

    @Then("the response body {string} = {int}")
    public void response_body_equals_int(String path, int expected) {
        assertEquals(Integer.valueOf(expected), then.extract().path(path));
    }

    @Then("the response body {string} = {float} as float")
    public void response_body_equals_float(String path, float expected) {
        assertEquals(Float.valueOf(expected), then.extract().path(path));
    }

    @Then("wait (\\d+) ms")
    public void wait_seconds(Integer ms) {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}