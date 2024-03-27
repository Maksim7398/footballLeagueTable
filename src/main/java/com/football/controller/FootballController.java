package com.football.controller;

import com.football.controller.request.CreateMatchRequest;
import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeam;

import com.football.mapper.MatchMapper;
import com.football.mapper.TeamMapper;
import com.football.service.match.FootballServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FootballController {

    private final FootballServiceImpl matchService;

    private final MatchMapper matchMapper;

    private final TeamMapper teamMapper;

    @PostMapping("/match")
    public GetResponseMatch createMatch(@RequestBody @Valid CreateMatchRequest createMatchRequest) {
        return matchMapper.matchDTOToGetResponseMatch(
                matchService.createGame(createMatchRequest.getHomeTeam(), createMatchRequest.getAwayTeam(),
                        createMatchRequest.getHomeGoals(), createMatchRequest.getAwayGoals()));
    }

    @GetMapping("/team")
    public List<GetResponseTeam> getTeamsTable(@RequestHeader @Nullable String localDateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now().format(formatter);
        }

        return teamMapper
                .convertDtoToResponseList(
                        matchService.createResultTeamTable(LocalDateTime.parse(localDateTime, formatter))
                );

    }

    @GetMapping("/match")
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
