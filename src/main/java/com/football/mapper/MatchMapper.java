package com.football.mapper;

import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeam;
import com.football.persist.entity.MatchEntity;
import com.football.model.MatchDTO;
import com.football.model.TeamDTO;

import java.util.List;

public interface MatchMapper {

    GetResponseTeam convertDtoToResponseTeam(TeamDTO teamDTO);

    List<GetResponseMatch> convertMatchDtoToResponse(List<MatchDTO> matchDTO);

    List<MatchDTO> convertMatchEntityToDto(List<MatchEntity> matchEntity);
}
