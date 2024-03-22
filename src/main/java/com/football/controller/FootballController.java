package com.football.controller;

import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeam;

import com.football.mapper.MatchMapper;
import com.football.mapper.TeamMapper;
import com.football.service.match.MatchServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FootballController {

    private final MatchServiceImpl matchService;

    private final MatchMapper matchMapper;

    private final TeamMapper teamMapper;

    @PostMapping("/match")
    public GetResponseMatch createMatch(@RequestParam String homeGoals,
                                        @RequestParam String awayGoals,
                                        @RequestParam UUID homeTeam,
                                        @RequestParam UUID awayTeam) {
        return matchMapper.matchDTOToGetResponseMatch(
                matchService.createGame(homeTeam, awayTeam, Integer.parseInt(homeGoals), Integer.parseInt(awayGoals)));
    }

    @PostMapping("/teamTable")
    public List<GetResponseTeam> getTeamsTable(@RequestHeader @Nullable String localDateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now().format(formatter);
        }

        return teamMapper
                .convertDtoToResponseList(
                        matchService.createResultTeam(LocalDateTime.parse(localDateTime, formatter))
                );

    }

    @PostMapping("/matchesResult")
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
}
