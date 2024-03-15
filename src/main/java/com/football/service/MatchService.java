package com.football.service;

import com.football.persist.entity.Match;
import com.football.persist.entity.Team;

import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final Map<String, Map<LocalDateTime, List<Team>>> result;

    private final CreateTableLeague createTableLeague;

    public Map<String, List<Team>> createMatch(final UUID team1, final UUID team2) throws InterruptedException {
        final Team zenit = teamRepository.findById(team1).get();
        final Team spartak = teamRepository.findById(team2).get();

        final Match match1 = new Match();
        match1.result(zenit, spartak, (int) (Math.random() * 10), (int) (Math.random() * 10));
        final Match match2 = new Match();
        match2.result(spartak, zenit, (int) (Math.random() * 10), (int) (Math.random() * 10));

        final List<Match> matchList = List.of(match1, match2);

        final Map<String, List<Team>> collect = matchList.stream().collect(Collectors.toMap(
                        m -> m.getAwayGoals() + ":" + m.getHomeGoals(),
                        c -> List.of(c.getAwayTeam(), c.getHomeTeam())
                )
        );

        teamRepository.saveAll(List.of(zenit, spartak));
        matchRepository.saveAll(matchList);
        CheckedPersonalMeeting.comparePoints(matchList, spartak, zenit);

        return collect;
    }

    public Map<String, Map<LocalDateTime, List<Team>>> result(LocalDateTime localDate) {
        final Map<String, Map<LocalDateTime, List<Team>>> collect2 =
                matchRepository.findAll().stream()
                        .filter(m -> m.getDateMatch().isBefore(localDate))
                .collect(Collectors.toMap(
                                m -> m.getAwayGoals() + ":" + m.getHomeGoals(),
                                c -> Map.of(c.getDateMatch(), List.of(c.getAwayTeam(), c.getHomeTeam()))
                        )
                );
        result.putAll(collect2);
        createTableLeague.createTable(result);
        return result;
    }

}
