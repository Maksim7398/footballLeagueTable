package com.football.data;

import com.football.model.MatchEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MatchRepositoryTest {

    private final MatchRepository matchRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public MatchRepositoryTest(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @Test
    void checkCreateTable(){
        final List<TeamEntity> teamEntities = teamRepository.findAll();
        final MatchEntity matchEntity = MatchEntityBuilder.aMatchEntityBuilder().
                withAwayTeam(teamEntities.get(0)).
                withHomeTeam(teamEntities.get(1)).build();

        matchRepository.save(matchEntity);

        final List<MatchEntity> matchEntities = matchRepository.findAll();
        assertNotNull(matchEntities);
        assertFalse(matchEntities.isEmpty());
    }
}
