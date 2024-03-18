package com.football.controller;
import com.football.persist.entity.Match;
import com.football.persist.entity.Team;
import com.football.service.MatchService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/match")
    public Map<String, List<Team>> createMatch(@RequestParam UUID team1,@RequestParam UUID team2){
       return matchService.createGame(team1,team2);
    }

    @GetMapping("/result")
    public   List<Match> result (@RequestHeader @Nullable String localDateTime){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (localDateTime == null){
            localDateTime = LocalDateTime.now().format(formatter);
        }
        return matchService.result(LocalDateTime.parse(localDateTime, formatter));
    }
}
