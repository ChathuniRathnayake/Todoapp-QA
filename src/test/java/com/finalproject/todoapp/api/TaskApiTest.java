package com.finalproject.todoapp.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskApiTest {

    private static int taskId;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @Order(1)
    void createTaskTest() {
        String taskName = "API Task " + System.currentTimeMillis();

        Response res = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + taskName + "\"}")
                .when()
                .post("/api/tasks")
                .then()
                .statusCode(201) // updated to match API behavior
                .body("name", equalTo(taskName))
                .extract().response();

        taskId = res.path("id"); // save for delete test
        Assertions.assertTrue(taskId > 0);
    }

    @Test
    @Order(2)
    void createTaskWithEmptyName_shouldReturn400() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"\"}")   // empty name
                .when()
                .post("/api/tasks")
                .then()
                .statusCode(400)           // expect Bad Request
                .body("error", containsString("Task name cannot be empty"));
    }

    @Test
    @Order(3)
    void deleteTaskTest() {
        // delete the task created in test 1
        given()
                .when()
                .delete("/api/tasks/" + taskId)
                .then()
                .statusCode(204); // expect No Content on success

        // confirm the task is gone
        given()
                .when()
                .get("/api/tasks/" + taskId)
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    void deleteNonExistingTask_shouldReturn404() {
        int nonExisting = 9999999; // very unlikely to exist
        given()
                .when()
                .delete("/api/tasks/" + nonExisting)
                .then()
                .statusCode(404)
                .body("error", containsString("not found"));
    }
}
