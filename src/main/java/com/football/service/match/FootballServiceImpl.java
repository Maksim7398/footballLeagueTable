package com.football.service.match;

import com.football.exception.MatchExceptions;
import com.football.exception.TeamNotFoundException;
import com.football.mapper.MatchMapper;
import com.football.mapper.TeamMapper;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;

import com.football.model.MatchDTO;
import com.football.model.TeamDTO;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.service.CheckedPersonalMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FootballServiceImpl {

    private final MatchRepository matchRepository;

    private final TeamRepository teamRepository;

    private final MatchMapper matchMapper;

    private final TeamMapper teamMapper;

    @Transactional
    public MatchDTO createGame(final String homeTeam, final String awayTeam, final Integer homeGoals, final Integer awayGoals) {
        final TeamEntity teamEntity1 = teamRepository.findTeamEntityByName(homeTeam)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));
        final TeamEntity teamEntity2 = teamRepository.findTeamEntityByName(awayTeam)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));

        matchRepository.findAllByFetch().forEach(m -> {
            if (m.getHomeTeam().equals(teamEntity1) && m.getAwayTeam().equals(teamEntity2)) {
                throw new MatchExceptions("Эти команды уже соревновались");
            }
        });

        final MatchEntity matchEntity = createResultTeam(teamEntity1, teamEntity2, homeGoals, awayGoals);
        matchRepository.save(matchEntity);

        return matchMapper.convertEntityToDto(matchEntity);
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

    public List<MatchDTO> getMatchesResult(LocalDateTime localDate) {
        final List<MatchEntity> matchEntities = matchRepository.findAllByFetch().stream()
                .sorted()
                .filter(m -> m.getDateMatch().isBefore(localDate))
                .toList();

        if (matchEntities.isEmpty()) {
            throw new MatchExceptions("Ни одного матча не было проведено");
        }

        return matchMapper.convertMatchEntityToDto(matchEntities);
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

    private MatchEntity createResultTeam(final TeamEntity homeTeam, final TeamEntity awayTeam, int homeGoals, int awayGoals) {
        final MatchEntity matchEntity = new MatchEntity();
        matchEntity.setHomeTeam(homeTeam);
        matchEntity.setAwayTeam(awayTeam);
        matchEntity.setDateMatch(LocalDateTime.now());
        matchEntity.setHomeGoals(homeGoals);
        matchEntity.setAwayGoals(awayGoals);

        return matchEntity;
    }
}
