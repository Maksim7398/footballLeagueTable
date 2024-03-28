package com.football.service;

import com.football.exception.MatchExceptions;
import com.football.mapper.MatchMapper;
import com.football.mapper.TeamMapper;
import com.football.model.MatchEntityBuilder;
import com.football.model.TeamEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.service.match.FootballServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FootballServiceImplTest {
    @InjectMocks
    private FootballServiceImpl underTest;
    @Mock
    private MatchRepository matchRepositoryMock;
    @Mock
    private TeamRepository teamRepositoryMock;
    @Mock
    private MatchMapper matchMapperMock;

    @Mock
    private TeamMapper teamMapperMock;

    @Test
    public void createGame_test() {
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder()
                .withId(UUID.randomUUID())
                .withName("Zenit").build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withId(UUID.randomUUID())
                .withName("Spartak").build();

        when(teamRepositoryMock.findTeamEntityByName(team1.getName())).thenReturn(Optional.of(team1));
        when(teamRepositoryMock.findTeamEntityByName(team2.getName())).thenReturn(Optional.of(team2));

        underTest.createGame(team1.getName(), team2.getName(), 2, 1);

        ArgumentCaptor<MatchEntity> captor = ArgumentCaptor.forClass(MatchEntity.class);

        verify(matchMapperMock).convertEntityToDto(captor.capture());
        verify(matchRepositoryMock).save(captor.capture());

        MatchEntity actual = captor.getValue();

        assertEquals(actual.getHomeTeam(), team1);
        assertEquals(actual.getAwayTeam(), team2);
        assertEquals(actual.getHomeGoals(), 2);
        assertEquals(actual.getAwayGoals(), 1);
    }

    @Test
    public void createGame_returnThisTeamAlreadyCompeted_test() {
        final MatchEntity expected = MatchEntityBuilder
                .aMatchEntityBuilder()
                .withDateMatch(LocalDateTime.now()).build();

        when(teamRepositoryMock.findTeamEntityByName(any())).thenReturn(Optional.of(expected.getHomeTeam()));
        when(teamRepositoryMock.findTeamEntityByName(any())).thenReturn(Optional.of(expected.getAwayTeam()));
        when(matchRepositoryMock.findAllByFetch()).thenReturn(List.of(expected));

        assertThrows(MatchExceptions.class,
                () -> underTest.createGame(
                        expected.getHomeTeam().getName(), expected.getAwayTeam().getName(), 2, 1)
        );
    }

    @Test
    public void checkedResultOfTeamOnSpecificDate_test() {
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
                .withHomeGoals(1)
                .withAwayGoals(0)
                .withDateMatch(LocalDateTime.now().plusDays(1)).build();

        when(teamRepositoryMock.findAll()).thenReturn(List.of(team1, team2));
        when(matchRepositoryMock.findAllByFetch()).thenReturn(List.of(matchEntity, matchEntity2));

        underTest.createResultTeamTable(LocalDateTime.now());

        ArgumentCaptor<List<TeamEntity>> argumentCaptorTeam = ArgumentCaptor.forClass(List.class);
        verify(teamMapperMock).convertEntityToDtoList(argumentCaptorTeam.capture());

        final List<TeamEntity> actual = argumentCaptorTeam.getValue();

        assertThat(actual)
                .anySatisfy(t -> {
                    assertEquals(t.getNumberOfGames(), 1);
                });
    }

}
