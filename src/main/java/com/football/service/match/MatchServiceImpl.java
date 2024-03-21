package com.football.service.match;

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
import lombok.SneakyThrows;
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
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    private final TeamRepository teamRepository;

    private final MatchMapper matchMapper;

    private final TeamMapper teamMapper;

    @Transactional
    public Map<String, List<TeamEntity>> createGame(final UUID team1id, final UUID team2id) {
        final TeamEntity teamEntity1 = teamRepository.findById(team1id)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));
        final TeamEntity teamEntity2 = teamRepository.findById(team2id)
                .orElseThrow(() -> new TeamNotFoundException("Такой команды не существует"));

        matchRepository.findAll().forEach(m -> {
            if (m.getHomeTeamEntity().equals(teamEntity1) && m.getAwayTeamEntity().equals(teamEntity2)) {
                throw new RuntimeException("Эти команды уже соревновались");
            }
        });

        final MatchEntity matchEntity1 = createMatch(teamEntity1, teamEntity2, (int) (Math.random() * 15), (int) (Math.random() * 15));
        final MatchEntity matchEntity2 = createMatch(teamEntity2, teamEntity1, (int) (Math.random() * 15), (int) (Math.random() * 15));
        final List<MatchEntity> matchEntityList = List.of(matchEntity1, matchEntity2);
        final Map<String, List<TeamEntity>> collect = matchEntityList.stream().collect(Collectors.toMap(
                        m -> m.getAwayGoals() + ":" + m.getHomeGoals(),
                        c -> List.of(c.getAwayTeamEntity(), c.getHomeTeamEntity())
                )
        );
        CheckedPersonalMeeting.comparePoints(matchEntityList, teamEntity1, teamEntity2);
        teamRepository.saveAll(List.of(teamEntity1, teamEntity2));
        matchRepository.saveAll(matchEntityList);
        CheckedPersonalMeeting.comparePoints(matchEntityList, teamEntity2, teamEntity1);

        return collect;
    }

    @Transactional
    public void globalGame() {
        final List<TeamEntity> teamEntities = teamRepository.findAll();
        createGlobalGame(teamEntities.toArray(new TeamEntity[0]), new ArrayList<>());
    }

    public List<TeamDTO> getTeamsTable()  {
        final List<TeamEntity> teamEntities = teamRepository.findAll().stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        return teamMapper.convertEntityToDtoList(teamEntities);
    }
    @SneakyThrows
    public List<MatchDTO> getMatchesResult(LocalDateTime localDate) {
        final List<MatchEntity> matchEntities = matchRepository.findAllByFetch().stream()
                .filter(m -> m.getDateMatch().isBefore(localDate))
                .toList();

        return matchMapper.convertMatchEntityToDto(matchEntities);
    }

    private MatchEntity createMatch(TeamEntity teamEntity1, TeamEntity teamEntity2, int homeGoals, int awayGoals) {
        final MatchEntity matchEntity = new MatchEntity();
        matchEntity.setAwayTeamEntity(teamEntity1);
        matchEntity.setHomeTeamEntity(teamEntity2);
        matchEntity.setDateMatch(LocalDateTime.now());
        matchEntity.setHomeGoals(homeGoals);
        matchEntity.setAwayGoals(awayGoals);
        if (homeGoals < awayGoals) {
            teamEntity1.setCountPoints(3);
            teamEntity1.setOtherPoints(3);
        }
        if (homeGoals > awayGoals) {
            teamEntity2.setCountPoints(3);
            teamEntity2.setOtherPoints(3);
        }
        if (homeGoals == awayGoals) {
            teamEntity1.setCountPoints(1);
            teamEntity2.setCountPoints(1);
            teamEntity1.setOtherPoints(1);
            teamEntity2.setOtherPoints(1);
        }
        teamEntity1.setNumberOfGames(1);
        teamEntity2.setNumberOfGames(1);
        teamEntity2.setScipGoals(awayGoals);
        teamEntity1.setScipGoals(homeGoals);
        teamEntity2.setTotalGoals(homeGoals);
        teamEntity1.setTotalGoals(awayGoals);
        return matchEntity;
    }

    private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    private void createGlobalGame(TeamEntity[] teamEntities, List<MatchEntity> matchEntityList) {
        final LocalDateTime localDateTime =
                LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0);

        final LocalDate now = LocalDate.now();

        int numOfTeams = teamEntities.length;
        final TeamEntity[] evenTeamEntities;
        int k = 0;
        if (numOfTeams % 2 == 0) {
            evenTeamEntities = new TeamEntity[numOfTeams - 1];
            for (k = 0; k < numOfTeams - 1; k++)
                evenTeamEntities[k] = teamEntities[k + 1];
        } else {
            evenTeamEntities = new TeamEntity[numOfTeams];
        }
        int teamsSize = evenTeamEntities.length;
        int total = ((teamsSize + 1) - 1);
        int halfSize = (teamsSize + 1) / 2;
        for (int week = total - 1; week >= 0; week--) {
            int teamIdx = week % teamsSize;
            final MatchEntity matchEntity = createMatch(teamEntities[0], evenTeamEntities[teamIdx],
                    (int) (Math.random() * 10), (int) (Math.random() * 10));
            matchEntity.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
            matchEntityList.add(matchEntity);

            final MatchEntity matchEntity1 = createMatch(evenTeamEntities[teamIdx], teamEntities[0],
                    (int) (Math.random() * 10), (int) (Math.random() * 10));
            matchEntity1.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
            matchEntityList.add(matchEntity1);

            CheckedPersonalMeeting.comparePoints(matchEntityList, teamEntities[0], evenTeamEntities[teamIdx]);

            for (int i = 1; i < halfSize; i++) {
                int firstTeam = (week + i) % teamsSize;
                int secondTeam = (week + teamsSize - i) % teamsSize;

                final MatchEntity matchEntity2 = createMatch(evenTeamEntities[firstTeam], evenTeamEntities[secondTeam],
                        (int) (Math.random() * 10), (int) (Math.random() * 10));
                matchEntity2.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
                matchEntityList.add(matchEntity2);

                final MatchEntity matchEntity3 = createMatch(evenTeamEntities[secondTeam], evenTeamEntities[firstTeam],
                        (int) (Math.random() * 10), (int) (Math.random() * 10));
                matchEntity3.setDateMatch(between(LocalDate.from(localDateTime), now).atStartOfDay());
                matchEntityList.add(matchEntity3);

                CheckedPersonalMeeting.comparePoints(matchEntityList, evenTeamEntities[firstTeam], evenTeamEntities[secondTeam]);
            }
        }
        teamRepository.saveAll(List.of(teamEntities));
        matchRepository.saveAll(matchEntityList);
    }
}
