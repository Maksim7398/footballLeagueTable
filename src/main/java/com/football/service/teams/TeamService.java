package com.football.service.teams;

import com.football.controller.request.CreateTeamRequest;
import com.football.exception.TeamNotFoundException;
import com.football.mapper.TeamMapper;
import com.football.model.TeamDTO;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.service.CheckedPersonalMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;

    private final TeamMapper teamMapper;

    public UUID createTeam(final CreateTeamRequest createTeamRequest) {
        final TeamDTO teamDTO = teamMapper.convertCreateTeamFromDto(createTeamRequest);
        final TeamEntity save = teamRepository.save(teamMapper.convertDtoFromTeam(teamDTO));

        return save.getId();
    }

    public List<TeamDTO> createResultTeamTable(final LocalDateTime localDate) {
        final List<TeamEntity> teamEntities = teamRepository.findAll();
        final List<MatchEntity> matchRepositoryAllByFetch = matchRepository.findAllByFetch();

        if (teamEntities.isEmpty()) {
            throw new TeamNotFoundException("Ни одной команды не зарегестрировано");
        }
        final Set<TeamDTO> teamDTOS = new HashSet<>();

        teamEntities.forEach(t -> {
            final TeamDTO teamDTO = teamMapper.convertEntityToDto(t);

            matchRepository.findMatchByHomeTeam(t).stream()
                    .filter(m -> m.getDateMatch().isBefore(localDate))
                    .forEach(m -> {
                        TeamDTO awayTeam = teamMapper.convertEntityToDto(m.getAwayTeam());
                        if (m.getHomeTeam().getName().equals(teamDTO.getName())) {
                            createTableTeam(
                                    teamDTO,
                                    awayTeam,
                                    m.getHomeGoals(),
                                    m.getAwayGoals()
                            );
                        }
                        CheckedPersonalMeeting.comparePoints(matchRepositoryAllByFetch, teamDTO, awayTeam);
                    });

            matchRepository.findMatchByAwayTeam(t).stream()
                    .filter(m -> m.getDateMatch().isBefore(localDate))
                    .forEach(m -> {
                        TeamDTO homeTeam = teamMapper.convertEntityToDto(m.getHomeTeam());
                        if (m.getAwayTeam().getName().equals(teamDTO.getName())) {
                            createTableTeam(
                                    homeTeam,
                                    teamDTO,
                                    m.getHomeGoals(),
                                    m.getAwayGoals()
                            );
                        }
                        CheckedPersonalMeeting.comparePoints(matchRepositoryAllByFetch, homeTeam, teamDTO);
                    });

            teamDTOS.add(teamDTO);
        });

        return teamDTOS.stream().sorted(Comparator.reverseOrder()).toList();
    }

    private void createTableTeam(final TeamDTO homeTeam, final TeamDTO awayTeam, int homeGoals, int awayGoals) {
        if (homeGoals > awayGoals) {
            homeTeam.setPoints(homeTeam.getPoints() + 3);
        }
        if (homeGoals < awayGoals) {
            awayTeam.setPoints(awayTeam.getPoints() + 3);
        }
        if (homeGoals == awayGoals) {
            homeTeam.setPoints(homeTeam.getPoints() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
        }
        homeTeam.setNumberOfGames(homeTeam.getNumberOfGames() + 1);
        awayTeam.setNumberOfGames(awayTeam.getNumberOfGames() + 1);
        awayTeam.setScipGoals(awayTeam.getScipGoals() + homeGoals);
        homeTeam.setScipGoals(homeTeam.getScipGoals() + awayGoals);
        awayTeam.setTotalGoals(awayTeam.getTotalGoals() + awayGoals);
        homeTeam.setTotalGoals(homeTeam.getTotalGoals() + homeGoals);
    }
}
