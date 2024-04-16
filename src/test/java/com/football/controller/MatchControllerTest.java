package com.football.controller;

import com.football.controller.request.CreateMatchRequest;
import com.football.model.CreateMatchRequestBuilder;
import com.football.model.MatchEntityBuilder;
import com.football.model.TeamEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
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
public class MatchControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

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
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder()
                .withName("JAVA").build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withId(UUID.randomUUID())
                .withName("SQL")
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
    public void getMatchTable_thenReturnOk_test() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withName("Spartak")
                .build();
        final MatchEntity matchEntityStub = MatchEntityBuilder.aMatchEntityBuilder()
                .withAwayTeam(team1)
                .withHomeTeam(team2).build();

        teamRepository.saveAll(List.of(team1, team2));
        matchRepository.save(matchEntityStub);

        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .header("localDateTimeForMatches", LocalDateTime.now().plusDays(1).format(formatter))
                .contentType(ContentType.JSON)
                .when()
                .get("/match")
                .then()
                .statusCode(200);
    }
}
