package com.football.service;

import com.football.persist.entity.Match;
import com.football.persist.entity.Team;

import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final TeamRepository teamRepository;

    private final List<Match> result;

    private final CreateTableLeague createTableLeague;
    @Transactional
    public Map<String, List<Team>> createGame(final UUID team1id, final UUID team2id) {
        final Team team1 = teamRepository.findById(team1id).get();
        final Team team2 = teamRepository.findById(team2id).get();

        matchRepository.findAll().forEach(m -> {
            if (m.getHomeTeam().equals(team1) && m.getAwayTeam().equals(team2)){
                throw new RuntimeException("Эти команды уже соревновались");
            }
        });

        final Match match1 = createMatch(team1, team2,1, 2);
        final Match match2 = createMatch(team2, team1,0,1);
        final List<Match> matchList = List.of(match1, match2);
        final Map<String, List<Team>> collect = matchList.stream().collect(Collectors.toMap(
                        m -> m.getAwayGoals() + ":" + m.getHomeGoals(),
                        c -> List.of(c.getAwayTeam(), c.getHomeTeam())
                )
        );

        teamRepository.saveAll(List.of(team1, team2));
        matchRepository.saveAll(matchList);
        CheckedPersonalMeeting.comparePoints(matchList, team2, team1);

        return collect;
    }

    public List<Match> result(LocalDateTime localDate) {
        final List<Match> matches = new java.util.ArrayList<>(matchRepository.findAll().stream().filter(m -> m.getDateMatch().isBefore(localDate))
                .toList());
        result.addAll(matches);
        createTableLeague.getStandings(teamRepository.findAll());
        createTableLeague.createTableMatch(matches);
        return result;
    }
    public Match createMatch(Team team1,Team team2, int homeGoals, int awayGoals){
        final Match match = new Match();
        match.setAwayTeam(team1);
        match.setHomeTeam(team2);
        match.setDateMatch(LocalDateTime.now());
        match.setHomeGoals(homeGoals);
        match.setAwayGoals(awayGoals);
        if (homeGoals < awayGoals){
            team1.setCountPoints(3);
            team1.setOtherPoints(3);
        }
        if (homeGoals > awayGoals) {
            team2.setCountPoints(3);
            team2.setOtherPoints(3);
        }
        if(homeGoals == awayGoals) {
            team1.setCountPoints(1);
            team2.setCountPoints(1);
            team1.setOtherPoints(1);
            team2.setOtherPoints(1);
        }
        team1.setNumberOfGames(1);
        team2.setNumberOfGames(1);
        team2.setScipGoals(awayGoals);
        team1.setScipGoals(homeGoals);
        team2.setTotalGoals(homeGoals);
        team1.setTotalGoals(awayGoals);
        return match;
    }
}
