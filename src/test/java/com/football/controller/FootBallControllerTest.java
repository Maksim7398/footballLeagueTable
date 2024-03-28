package com.football.controller;

import com.football.controller.request.CreateMatchRequest;
import com.football.model.CreateMatchRequestBuilder;
import com.football.model.TeamEntityBuilder;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.TeamRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FootBallControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void createMatch_returnTeamNotFoundException_test() {
        final CreateMatchRequest createMatchRequest = CreateMatchRequestBuilder.aCreateMatchRequestBuilder().build();
        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .contentType(ContentType.JSON)
                .when()
                .body(createMatchRequest)
                .post("/match")
                .then()
                .statusCode(404);
    }

    @Test
    public void createMatch_returnStatusOk_test() {
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withName("Spartak")
                .build();

        final CreateMatchRequest createMatchRequest = CreateMatchRequestBuilder.aCreateMatchRequestBuilder()
                .withHomeTeam(team1.getName())
                .withAwayTeam(team2.getName())
                .build();

        teamRepository.saveAll(List.of(team1, team2));
        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .contentType(ContentType.JSON)
                .when()
                .body(createMatchRequest)
                .post("/match")
                .then()
                .statusCode(200);
    }

    @Test
    public void getTeamsTable_test() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .contentType(ContentType.JSON)
                .when()
                .header("localDateTime", LocalDateTime.now().format(formatter))
                .get("/team")
                .then()
                .statusCode(200);
    }
}
