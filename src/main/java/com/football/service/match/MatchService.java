package com.football.service.match;

import com.football.exception.MatchExceptions;
import com.football.exception.TeamNotFoundException;
import com.football.exception.TournamentNotFoundException;
import com.football.mapper.MatchMapper;
import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;

import com.football.model.MatchDTO;
import com.football.persist.entity.Tournament;
import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import com.football.persist.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final TeamRepository teamRepository;

    private final MatchMapper matchMapper;

    private final TournamentRepository tournamentRepository;

    @Transactional
    public MatchDTO createGame(final Long tournamentId, final String homeTeam, final String awayTeam, final Integer homeGoals, final Integer awayGoals) {
        final TeamEntity teamEntity1 = teamRepository.findTeamEntityByName(homeTeam)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));
        final TeamEntity teamEntity2 = teamRepository.findTeamEntityByName(awayTeam)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));

        final Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Такого турнира не существует"));

        matchRepository.findAllByFetch().stream().filter(m -> m.getTournament().equals(tournament)).forEach(m -> {
            if (m.getHomeTeam().equals(teamEntity1) && m.getAwayTeam().equals(teamEntity2)) {
                throw new MatchExceptions("Эти команды уже соревновались");
            }
        });

        final MatchEntity matchEntity = createResultTeam(teamEntity1, teamEntity2, homeGoals, awayGoals);

        matchEntity.setTournament(tournament);

        matchRepository.save(matchEntity);

        return matchMapper.convertEntityToDto(matchEntity);
    }

    @Transactional
    public List<MatchDTO> getMatchesResult(Long tournamentId, LocalDateTime localDate) {
        final List<MatchEntity> matchEntities = matchRepository.findAllByFetch().stream()
                .sorted()
                .filter(m -> m.getDateMatch().isBefore(localDate.plusMinutes(5)))
                .filter(m -> m.getTournament().getId().equals(tournamentId))
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

    public List<MatchDTO> getMatchResultByTeam(final UUID teamId) {
        final List<MatchEntity> matchEntities = matchRepository.findAllMatchByTeam(teamId);

        return matchMapper.convertMatchEntityToDto(matchEntities);
    }
}
