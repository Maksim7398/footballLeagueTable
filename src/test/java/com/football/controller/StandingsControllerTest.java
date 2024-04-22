package com.football.controller;

import com.football.model.MatchEntityBuilder;
import com.football.model.TeamEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.entity.Tournament;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.persist.repository.TournamentRepository;
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
public class StandingsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    public void getStandings_test() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withName("Spartak")
                .build();
        final MatchEntity matchEntityStub = MatchEntityBuilder.aMatchEntityBuilder()
                .withAwayTeam(team1)
                .withHomeTeam(team2).build();

        teamRepository.saveAll(List.of(team1, team2));
        tournamentRepository.save(new Tournament(1L,"Russia"));
        matchRepository.save(matchEntityStub);

        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .header("localDateTime", LocalDateTime.now().plusDays(1).format(formatter))
                .contentType(ContentType.JSON)
                .when()
                .get("/standings/Russia")
                .then()
                .statusCode(200);
    }
}
