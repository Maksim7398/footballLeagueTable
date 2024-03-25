package com.football.controller;

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
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FootBallControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void createMatch_returnTeamNotFoundException_test() {
        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .contentType(ContentType.JSON)
                .when()
                .queryParam("homeTeam", UUID.randomUUID())
                .queryParam("awayTeam", UUID.randomUUID())
                .queryParam("homeGoals", 1)
                .queryParam("awayGoals", 0)
                .post("/createMatch")
                .then()
                .statusCode(404);
    }

    @Test
    public void createMatch_returnStatusOk_test() {
        TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().build();
        TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withName("Spartak")
                .build();
        teamRepository.saveAll(List.of(team1, team2));
        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .contentType(ContentType.JSON)
                .when()
                .queryParam("homeTeam", team1.getId())
                .queryParam("awayTeam", team2.getId())
                .queryParam("homeGoals", 1)
                .queryParam("awayGoals", 0)
                .post("/createMatch")
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
                .post("/teamTable")
                .then()
                .statusCode(200);
    }
}
