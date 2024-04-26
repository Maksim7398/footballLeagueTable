package com.football.controller;

import com.football.controller.date_format.DateFormat;
import com.football.controller.request.CreateMatchRequest;

import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.MatchTable;
import com.football.controller.universal_response.UniversalResponse;
import com.football.controller.universal_response.UniversalSuccessResponse;
import com.football.mapper.MatchMapper;
import com.football.model.MatchDTO;
import com.football.service.match.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    private final MatchMapper matchMapper;

    private final DateFormat dateFormat;

    @PostMapping
    public UniversalResponse<GetResponseMatch> createMatch(@RequestBody @Valid CreateMatchRequest createMatchRequest) {
        final GetResponseMatch payload = matchMapper.matchDTOToGetResponseMatch(
                matchService.createGame(createMatchRequest.getTournamentId(), createMatchRequest.getHomeTeam(), createMatchRequest.getAwayTeam(),
                        createMatchRequest.getHomeGoals(), createMatchRequest.getAwayGoals()));

        return new UniversalSuccessResponse<>(HttpStatus.OK, payload);
    }

    @GetMapping("/{tournamentId}")
    public UniversalResponse<MatchTable> getMatchesResult(@RequestParam(value = "dateMatch") @Nullable String localDateTimeForMatches,
                                                          @PathVariable Long tournamentId) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.getFormat());
        if (localDateTimeForMatches == null) {
            localDateTimeForMatches = LocalDateTime.now().format(formatter);
        }
        final List<GetResponseMatch> matchList = matchMapper
                .convertMatchDtoToResponseList(matchService
                        .getMatchesResult(tournamentId, LocalDateTime.parse(localDateTimeForMatches, formatter)));

        return new UniversalSuccessResponse<>(HttpStatus.OK, MatchTable.builder()
                .tournamentName(matchList.stream()
                        .map(m -> m.getTournament().getName())
                        .findFirst().get())
                .calculateDate(localDateTimeForMatches)
                .matchList(matchList)
                .build());
    }

    @GetMapping("/team/{teamId}")
    public UniversalResponse<MatchTable> getAllMatchByTeam(@PathVariable UUID teamId) {
        final List<MatchDTO> matchResultByTeam = matchService.getMatchResultByTeam(teamId);

        return new UniversalSuccessResponse<>(HttpStatus.OK, MatchTable.builder()
                .tournamentName(matchResultByTeam.stream()
                        .map(m -> m.getTournament().getName())
                        .findFirst().get())
                .calculateDate(matchResultByTeam.stream().map(MatchDTO::getDateMatch).findFirst().toString())
                .matchList(matchMapper.convertMatchDtoToResponseList(matchResultByTeam))
                .build());
    }
}
