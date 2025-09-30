package com.finalproject.todoapp.steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
public class TaskSteps {
    private Response response;
    @Given("the application is running")
    public void app_running() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }
    @When("I send POST /api/tasks with body {string}")
    public void send_post(String body) {
        response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .when().post("/api/tasks");
    }

    @Then("the response status should be {int}")
    public void status_is(int code) {
        assertEquals(code, response.statusCode());
    }

    @Then("the response should contain {string}")
    public void body_contains(String text) {
        assertTrue(response.asString().contains(text));
    }
}