package com.football.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class  TeamControllerTest {


    @LocalServerPort
    private int port;

    @Test
    public void getTeamsTable_test() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .header("localDateTime", LocalDateTime.now().format(formatter))
                .contentType(ContentType.JSON)
                .when()
                .get("/team")
                .then()
                .statusCode(200);
    }
}
