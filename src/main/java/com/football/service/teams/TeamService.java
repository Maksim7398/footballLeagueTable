package com.football.service.teams;

import com.football.controller.request.CreateTeamRequest;
import com.football.exception.TeamNotFoundException;
import com.football.mapper.TeamMapper;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.model.TeamDTO;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.service.CheckedPersonalMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;

    private final TeamMapper teamMapper;

    public UUID createTeam(final CreateTeamRequest createTeamRequest){
        final TeamDTO teamDTO = teamMapper.convertCreateTeamFromDto(createTeamRequest);
        teamDTO.setOtherPoints(0);
        teamDTO.setPoints(0);
        final TeamEntity save = teamRepository.save(teamMapper.convertDtoFromTeam(teamDTO));

        return save.getId();
    }

    public List<TeamDTO> createResultTeamTable(final LocalDateTime localDate) {
        final List<MatchEntity> matchEntities = matchRepository.findAllByFetch();
        final List<TeamEntity> teamEntities = teamRepository.findAll();
        if (teamEntities.isEmpty()) {
            throw new TeamNotFoundException("Ни одной команды не зарегестрировано");
        }
        teamEntities.forEach(t -> {
            t.setTotalGoals(0);
            t.setPoints(0);
            t.setOtherPoints(0);
            t.setNumberOfGames(0);
            t.setScipGoals(0);
        });
        matchEntities.stream()
                .filter(m -> m.getDateMatch().isBefore(localDate)).
                forEach(m -> {
                    createTableTeam(m.getHomeTeam(), m.getAwayTeam(), m.getHomeGoals(), m.getAwayGoals());
                    if (m.getHomeTeam().getPoints().equals(m.getAwayTeam().getPoints())) {
                        CheckedPersonalMeeting.comparePoints(matchEntities, m.getHomeTeam(), m.getAwayTeam());
                    }
                });

        return teamMapper.convertEntityToDtoList(teamEntities.stream()
                .sorted(Comparator.reverseOrder())
                .toList());
    }

    private void createTableTeam(final TeamEntity homeTeam, final TeamEntity awayTeam, int homeGoals, int awayGoals) {
        if (homeGoals > awayGoals) {
            homeTeam.setPoints(homeTeam.getPoints() + 3);
            homeTeam.setOtherPoints(homeTeam.getOtherPoints() + 3);
        }
        if (homeGoals < awayGoals) {
            awayTeam.setPoints(awayTeam.getPoints() + 3);
            awayTeam.setOtherPoints(awayTeam.getOtherPoints() + 3);
        }
        if (homeGoals == awayGoals) {
            homeTeam.setPoints(homeTeam.getPoints() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
            homeTeam.setOtherPoints(homeTeam.getOtherPoints() + 1);
            awayTeam.setOtherPoints(awayTeam.getOtherPoints() + 1);
        }
        homeTeam.setNumberOfGames(homeTeam.getNumberOfGames() + 1);
        awayTeam.setNumberOfGames(awayTeam.getNumberOfGames() + 1);
        awayTeam.setScipGoals(awayTeam.getScipGoals() + homeGoals);
        homeTeam.setScipGoals(homeTeam.getScipGoals() + awayGoals);
        awayTeam.setTotalGoals(awayTeam.getTotalGoals() + awayGoals);
        homeTeam.setTotalGoals(homeTeam.getTotalGoals() + homeGoals);
    }
}
