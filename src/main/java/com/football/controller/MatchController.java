package com.football.controller;

import com.football.controller.request.CreateMatchRequest;

import com.football.controller.response.MatchTable;
import com.football.controller.response.UniversalResponse;
import com.football.mapper.MatchMapper;
import com.football.service.match.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    private final MatchMapper matchMapper;

    @Value("${format.date}")
    private String dateFormat;

    @PostMapping
    public UniversalResponse createMatch(@RequestBody @Valid CreateMatchRequest createMatchRequest) {
        return UniversalResponse.builder().status(HttpStatus.OK)
                .payload(matchMapper.matchDTOToGetResponseMatch(
                        matchService.createGame(createMatchRequest.getTournamentName(),createMatchRequest.getHomeTeam(), createMatchRequest.getAwayTeam(),
                                createMatchRequest.getHomeGoals(), createMatchRequest.getAwayGoals())))
                .errorDetails("")
                .build();
    }

    @GetMapping("/{tournamentName}")
    public UniversalResponse getMatchesResult(@RequestHeader @Nullable String localDateTimeForMatches,
                                              @PathVariable String tournamentName) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        if (localDateTimeForMatches == null) {
            localDateTimeForMatches = LocalDateTime.now().format(formatter);
        }
        return UniversalResponse.builder()
                .status(HttpStatus.OK)
                .payload(MatchTable.builder()
                        .tournamentName(tournamentName)
                        .calculateDate(localDateTimeForMatches)
                        .matchList(matchMapper
                                .convertMatchDtoToResponseList(matchService
                                        .getMatchesResult(tournamentName,LocalDateTime.parse(localDateTimeForMatches, formatter))))
                        .build())
                .errorDetails("")
                .build();
    }
}
