package com.football.controller;
import com.football.persist.entity.LeagueTable;
import com.football.persist.entity.Team;
import com.football.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FootballController {

    private final MatchService matchService;

    @GetMapping("/match")
    @SneakyThrows
    public LeagueTable createMatch(@RequestParam UUID team1,@RequestParam UUID team2){
       return matchService.createMatch(team1,team2);
    }

    @GetMapping("/result")
    public Map<String, Map<LocalDateTime, List<Team>>> result(){
        return matchService.result();
    }

}
