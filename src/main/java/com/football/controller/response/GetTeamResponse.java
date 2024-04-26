package com.football.controller.response;

import com.football.model.TeamDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetTeamResponse {

    private List<TeamDTO> teamDTOList;
}
