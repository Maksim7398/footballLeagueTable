package com.football.service.match;

import com.football.model.MatchDTO;
import com.football.model.TeamDTO;
import com.football.persist.entity.TeamEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MatchService {

    Map<String, List<TeamEntity>> createGame(UUID team1id, UUID team2id);

    void globalGame();

    List<TeamDTO> getTeamsTable();

    List<MatchDTO> getMatchesResult(LocalDateTime localDate);
}
