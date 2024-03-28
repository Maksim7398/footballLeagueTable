package com.football.data;

import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.yaml")
@ActiveProfiles("test")
public class TeamRepositoryTest {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamRepositoryTest(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Test
    void checkLoadData() {
        final List<TeamEntity> teamEntities = teamRepository.findAll();
        assertNotNull(teamEntities);

        assertEquals(teamEntities.size(), 22);
    }
}
