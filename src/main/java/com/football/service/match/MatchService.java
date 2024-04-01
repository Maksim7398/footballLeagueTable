package com.football.service.match;

import com.football.exception.MatchExceptions;
import com.football.exception.TeamNotFoundException;
import com.football.mapper.MatchMapper;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;

import com.football.model.MatchDTO;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final TeamRepository teamRepository;

    private final MatchMapper matchMapper;

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

    @Transactional
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
