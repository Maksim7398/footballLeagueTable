package com.football.controller.response;

import com.football.model.TeamDTO;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class TeamTable {

    private String calculateDate;

    private String tournamentName;

    private Map<Integer, TeamDTO> teamTable;
}
