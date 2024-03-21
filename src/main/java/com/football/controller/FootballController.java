package com.football.controller;

import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeam;

import com.football.mapper.MatchMapper;
import com.football.mapper.TeamMapper;
import com.football.persist.entity.TeamEntity;
import com.football.service.match.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FootballController {

    private final MatchService matchService;

    private final MatchMapper matchMapper;

    private final TeamMapper teamMapper;

    @GetMapping("/match")
    public Map<String, List<TeamEntity>> createMatch(@RequestParam UUID team1, @RequestParam UUID team2) {
        return matchService.createGame(team1, team2);
    }

    @GetMapping("/teamTable")
    @SneakyThrows
    public List<GetResponseTeam> getTeamsTable() {
        return teamMapper.convertDtoToResponseList(matchService.getTeamsTable());
    }

    @GetMapping("/matchesResult")
    @SneakyThrows
    public List<GetResponseMatch> getMatchesResult(@RequestHeader @Nullable String localDateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now().format(formatter);
        }

        return matchMapper
                .convertMatchDtoToResponseList(matchService
                        .getMatchesResult(LocalDateTime.parse(localDateTime, formatter))
                );
    }

    @GetMapping("/globalGame")
    public void createGlobalGame() {
        matchService.globalGame();
    }
}
