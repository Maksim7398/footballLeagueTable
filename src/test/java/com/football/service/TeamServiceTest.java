package com.football.service;

import com.football.mapper.TeamMapper;
import com.football.model.MatchEntityBuilder;
import com.football.model.TeamEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.service.teams.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @InjectMocks
    private TeamService underTest;
    @Mock
    private TeamRepository teamRepositoryMock;

    @Mock
    private TeamMapper teamMapperMock;

    @Mock
    private MatchRepository matchRepositoryMock;

    @Test
    public void checkedResultOfTeamOnSpecificDate_orCompareTotalGoals_test() {
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().withName("Zenit").build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder().withName("Spartak").build();
        final MatchEntity matchEntity = MatchEntityBuilder.aMatchEntityBuilder()
                .withAwayTeam(team1)
                .withHomeTeam(team2)
                .withHomeGoals(2)
                .withAwayGoals(1)
                .withDateMatch(LocalDateTime.now().minusDays(1)).build();

        final MatchEntity matchEntity2 = MatchEntityBuilder.aMatchEntityBuilder()
                .withAwayTeam(team2)
                .withHomeTeam(team1)
                .withHomeGoals(3)
                .withAwayGoals(1)
                .withDateMatch(LocalDateTime.now().plusDays(1)).build();

        when(teamRepositoryMock.findAll()).thenReturn(List.of(team1, team2));
        when(matchRepositoryMock.findAllByFetch()).thenReturn(List.of(matchEntity, matchEntity2));

        underTest.createResultTeamTable(LocalDateTime.now().plusDays(2));

        ArgumentCaptor<List<TeamEntity>> argumentCaptorTeam = ArgumentCaptor.forClass(List.class);
        verify(teamMapperMock).convertEntityToDtoList(argumentCaptorTeam.capture());

        final List<TeamEntity> actual = argumentCaptorTeam.getValue();

        assertEquals(team1, actual.get(0));
        assertThat(actual)
                .anySatisfy(t -> {
                    assertEquals(t.getNumberOfGames(), 2);
                });
    }

    @Test
    public void checkedResultOfTeamOnSpecificDate_orCompareAwayGoalsCount_test() {
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder().withName("Zenit").build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder().withName("Spartak").build();
        final MatchEntity matchEntity = MatchEntityBuilder.aMatchEntityBuilder()
                .withAwayTeam(team1)
                .withHomeTeam(team2)
                .withHomeGoals(1)
                .withAwayGoals(2)
                .withDateMatch(LocalDateTime.now().minusDays(1)).build();

        final MatchEntity matchEntity2 = MatchEntityBuilder.aMatchEntityBuilder()
                .withAwayTeam(team2)
                .withHomeTeam(team1)
                .withHomeGoals(0)
                .withAwayGoals(1)
                .withDateMatch(LocalDateTime.now().plusDays(1)).build();

        when(teamRepositoryMock.findAll()).thenReturn(List.of(team1, team2));
        when(matchRepositoryMock.findAllByFetch()).thenReturn(List.of(matchEntity, matchEntity2));

        underTest.createResultTeamTable(LocalDateTime.now().plusDays(2));

        ArgumentCaptor<List<TeamEntity>> argumentCaptorTeam = ArgumentCaptor.forClass(List.class);
        verify(teamMapperMock).convertEntityToDtoList(argumentCaptorTeam.capture());

        final List<TeamEntity> actual = argumentCaptorTeam.getValue();

        assertEquals(team1, actual.get(0));
    }
}
