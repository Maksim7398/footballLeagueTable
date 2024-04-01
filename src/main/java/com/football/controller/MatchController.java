package com.football.controller;

import com.football.controller.request.CreateMatchRequest;
import com.football.controller.response.GetResponseMatch;

import com.football.mapper.MatchMapper;
import com.football.service.match.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    private final MatchMapper matchMapper;

    @PostMapping
    public GetResponseMatch createMatch(@RequestBody @Valid CreateMatchRequest createMatchRequest) {
        return matchMapper.matchDTOToGetResponseMatch(
                matchService.createGame(createMatchRequest.getHomeTeam(), createMatchRequest.getAwayTeam(),
                        createMatchRequest.getHomeGoals(), createMatchRequest.getAwayGoals()));
    }

    @GetMapping
    public List<GetResponseMatch> getMatchesResult(@RequestHeader @Nullable String localDateTimeForMatches) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (localDateTimeForMatches == null) {
            localDateTimeForMatches = LocalDateTime.now().format(formatter);
        }

        return matchMapper
                .convertMatchDtoToResponseList(matchService
                        .getMatchesResult(LocalDateTime.parse(localDateTimeForMatches, formatter))
                );
    }
}
