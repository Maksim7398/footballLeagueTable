package com.football.controller;

import com.football.controller.date_format.DateFormat;
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
    public int port;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private DateFormat dateFormat;

    @Test
    public void getStandings_test() {
        matchRepository.deleteAll();
        teamRepository.deleteAll();

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.getFormat());
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withName("Spartak")
                .build();
        final MatchEntity matchEntityStub = MatchEntityBuilder.aMatchEntityBuilder()
                .withAwayTeam(team1)
                .withHomeTeam(team2)
                .withTournament(new Tournament(1L, "Russia"))
                .build();

        teamRepository.saveAll(List.of(team1, team2));
        tournamentRepository.save(new Tournament(1L, "Russia"));
        matchRepository.save(matchEntityStub);

        RestAssured.given()
                .baseUri("http://localhost:" + port + "/my-app")
                .param("dateMatch", LocalDateTime.now().format(formatter))
                .contentType(ContentType.JSON)
                .when()
                .get("/standings/1")
                .then()
                .statusCode(200);
    }
}
