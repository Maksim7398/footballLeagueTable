package com.football.service;

import com.football.exception.MatchExceptions;
import com.football.mapper.MatchMapper;
import com.football.model.MatchEntityBuilder;
import com.football.model.TeamEntityBuilder;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.entity.Tournament;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.persist.repository.TournamentRepository;
import com.football.service.match.MatchService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @InjectMocks
    private MatchService underTest;
    @Mock
    private MatchRepository matchRepositoryMock;
    @Mock
    private TeamRepository teamRepositoryMock;
    @Mock
    private TournamentRepository tournamentRepositoryMock;
    @Mock
    private MatchMapper matchMapperMock;

    @Test
    public void createMatch_test() {
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder()
                .withId(UUID.randomUUID())
                .withName("Zenit").build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withId(UUID.randomUUID())
                .withName("Spartak").build();
        final Tournament tournament = new Tournament(1L, "Russia");

        when(teamRepositoryMock.findById(team1.getId())).thenReturn(Optional.of(team1));
        when(teamRepositoryMock.findById(team2.getId())).thenReturn(Optional.of(team2));
        when(tournamentRepositoryMock.findById(any())).thenReturn(Optional.of(tournament));

        underTest.createGame(1L, team1.getId(), team2.getId(), 2, 1);

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
    public void createMatch_returnThisTeamAlreadyCompeted_test() {
        final TeamEntity team1 = TeamEntityBuilder.aTeamEntityBuilder()
                .withId(UUID.randomUUID())
                .withName("Zenit").build();
        final TeamEntity team2 = TeamEntityBuilder.aTeamEntityBuilder()
                .withId(UUID.randomUUID())
                .withName("Spartak").build();
        final Tournament tournament = new Tournament(1L, "Russia");

        final MatchEntity expected = MatchEntityBuilder
                .aMatchEntityBuilder()
                .withHomeTeam(team1)
                .withAwayTeam(team2)
                .withTournament(tournament)
                .withDateMatch(LocalDateTime.now()).build();


        when(teamRepositoryMock.findById(team1.getId())).thenReturn(Optional.of(team1));
        when(teamRepositoryMock.findById(team2.getId())).thenReturn(Optional.of(team2));
        when(tournamentRepositoryMock.findById(any())).thenReturn(Optional.of(tournament));
        when(matchRepositoryMock.findAllByFetch()).thenReturn(List.of(expected));

        assertThrows(MatchExceptions.class,
                () -> {
                    underTest.createGame(
                            1L,
                            team1.getId(),
                            team2.getId(),
                            2, 1);
                }
        );
    }
}
