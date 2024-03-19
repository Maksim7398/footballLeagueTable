package com.football.service;

import com.football.exception.TeamNotFoundException;
import com.football.persist.entity.Match;
import com.football.persist.entity.Team;

import com.football.persist.repository.MatchRepository;
import com.football.persist.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final TeamRepository teamRepository;


    private final CreateTableLeague createTableLeague;

    @Transactional
    public Map<String, List<Team>> createGame(final UUID team1id, final UUID team2id) {
        final Team team1 = teamRepository.findById(team1id)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));
        final Team team2 = teamRepository.findById(team2id)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));

        matchRepository.findAll().forEach(m -> {
            if (m.getHomeTeam().equals(team1) && m.getAwayTeam().equals(team2)) {
                throw new RuntimeException("Эти команды уже соревновались");
            }
        });

        final Match match1 = createMatch(team1, team2, (int) (Math.random() * 15), (int) (Math.random() * 15));
        final Match match2 = createMatch(team2, team1, (int) (Math.random() * 15), (int) (Math.random() * 15));
        final List<Match> matchList = List.of(match1, match2);
        final Map<String, List<Team>> collect = matchList.stream().collect(Collectors.toMap(
                        m -> m.getAwayGoals() + ":" + m.getHomeGoals(),
                        c -> List.of(c.getAwayTeam(), c.getHomeTeam())
                )
        );
        CheckedPersonalMeeting.comparePoints(matchList, team1, team2);
        teamRepository.saveAll(List.of(team1, team2));
        matchRepository.saveAll(matchList);
        CheckedPersonalMeeting.comparePoints(matchList, team2, team1);

        return collect;
    }

    @Transactional
    public void globalGame() {
        final List<Team> teams = teamRepository.findAll();
        createGlobalGame(teams.toArray(new Team[0]), new ArrayList<>());
    }

    public List<Team> getTeamsTable() {
        final List<Team> teams = teamRepository.findAll().stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        createTableLeague.getStandings(teams);
        return teams;
    }

    public List<Match> getMatchesResult(LocalDateTime localDate) {
        final List<Match> matches = matchRepository.findAllByFetch().stream()
                .filter(m -> m.getDateMatch().isBefore(localDate))
                .toList();
        createTableLeague.createTableMatch(matches);

        return matches;
    }


    private Match createMatch(Team team1, Team team2, int homeGoals, int awayGoals) {
        final Match match = new Match();
        match.setAwayTeam(team1);
        match.setHomeTeam(team2);
        match.setDateMatch(LocalDateTime.now());
        match.setHomeGoals(homeGoals);
        match.setAwayGoals(awayGoals);
        if (homeGoals < awayGoals) {
            team1.setCountPoints(3);
            team1.setOtherPoints(3);
        }
        if (homeGoals > awayGoals) {
            team2.setCountPoints(3);
            team2.setOtherPoints(3);
        }
        if (homeGoals == awayGoals) {
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

    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    @Transactional
    public void createGlobalGame(Team[] teams, List<Match> matchList) {
        final LocalDateTime localDateTime =
                LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0);

        final LocalDate now = LocalDate.now();

        int numOfTeams = teams.length;
        final Team[] evenTeams;
        int k = 0;
        if (numOfTeams % 2 == 0) {
            evenTeams = new Team[numOfTeams - 1];
            for (k = 0; k < numOfTeams - 1; k++)
                evenTeams[k] = teams[k + 1];
        } else {
            evenTeams = new Team[numOfTeams];
        }
        int teamsSize = evenTeams.length;
        int total = ((teamsSize + 1) - 1);
        int halfSize = (teamsSize + 1) / 2;
        for (int week = total - 1; week >= 0; week--) {
            int teamIdx = week % teamsSize;
            final Match match = createMatch(teams[0], evenTeams[teamIdx],
                    (int) (Math.random() * 10), (int) (Math.random() * 10));
            match.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
            matchList.add(match);

            final Match match1 = createMatch(evenTeams[teamIdx], teams[0],
                    (int) (Math.random() * 10), (int) (Math.random() * 10));
            match1.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
            matchList.add(match1);

            CheckedPersonalMeeting.comparePoints(matchList, teams[0], evenTeams[teamIdx]);

            for (int i = 1; i < halfSize; i++) {
                int firstTeam = (week + i) % teamsSize;
                int secondTeam = (week + teamsSize - i) % teamsSize;

                final Match match2 = createMatch(evenTeams[firstTeam], evenTeams[secondTeam],
                        (int) (Math.random() * 10), (int) (Math.random() * 10));
                match2.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
                matchList.add(match2);

                final Match match3 = createMatch(evenTeams[secondTeam], evenTeams[firstTeam],
                        (int) (Math.random() * 10), (int) (Math.random() * 10));
                match3.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
                matchList.add(match3);

                CheckedPersonalMeeting.comparePoints(matchList, evenTeams[firstTeam], evenTeams[secondTeam]);
            }
        }
        teamRepository.saveAll(List.of(teams));
        matchRepository.saveAll(matchList);
    }
}
