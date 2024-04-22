package com.football.data;

import com.football.model.MatchEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.persist.repository.TournamentRepository;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
public class MatchRepositoryTest {

    @Autowired
    private  MatchRepository matchRepository;

    @Autowired
    private  TeamRepository teamRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    void checkCreateTable(){
        final List<TeamEntity> teamEntities = teamRepository.findAll();
        final MatchEntity expected = MatchEntityBuilder.aMatchEntityBuilder().
                withAwayTeam(teamEntities.get(0)).
                withHomeTeam(teamEntities.get(1)).build();

        tournamentRepository.save(expected.getTournament());
        matchRepository.save(expected);

        final List<MatchEntity> actual = matchRepository.findAll();

        AssertionsForInterfaceTypes.assertThat(actual)
                        .anySatisfy(m -> {
                            assertEquals(expected.getAwayTeam(),m.getAwayTeam());
                            assertEquals(expected.getHomeTeam(),m.getHomeTeam());
                            assertEquals(expected.getAwayGoals(),m.getAwayGoals());
                        });
        assertFalse(actual.isEmpty());
    }
}
