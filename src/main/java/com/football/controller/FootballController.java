package com.football.controller;
import com.football.persist.entity.Team;
import com.football.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public Map<String, List<Team>> createMatch(@RequestParam UUID team1,@RequestParam UUID team2){
       return matchService.createMatch(team1,team2);
    }

    @GetMapping("/result")
    public Map<String, Map<LocalDateTime, List<Team>>> result
            (@RequestHeader String localDateTime){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return matchService.result(LocalDateTime.parse(localDateTime, formatter));
    }

}
