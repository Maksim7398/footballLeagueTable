package com.football.data;

import com.football.model.MatchEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
public class MatchRepositoryTest {

    @Autowired
    private  MatchRepository matchRepository;

    @Autowired
    private  TeamRepository teamRepository;


    @Test
    void checkCreateTable(){
        final List<TeamEntity> teamEntities = teamRepository.findAll();
        final MatchEntity expected = MatchEntityBuilder.aMatchEntityBuilder().
                withAwayTeam(teamEntities.get(0)).
                withHomeTeam(teamEntities.get(1)).build();

        matchRepository.save(expected);

        final List<MatchEntity> actual = matchRepository.findAll();

        assertFalse(actual.isEmpty());
    }
}
