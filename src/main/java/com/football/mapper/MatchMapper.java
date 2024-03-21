package com.football.mapper;

import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeam;
import com.football.persist.entity.MatchEntity;
import com.football.model.MatchDTO;
import com.football.persist.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    default GetResponseTeam convertEntityToResponseTeam(TeamEntity teamEntity) {
        return Mappers.getMapper(TeamMapper.class).convertEntityToResponseTeam(teamEntity);
    }

    default GetResponseMatch convertMatchEntityToResponse(MatchEntity matchEntity) {
        if (matchEntity == null) {
            return null;
        } else {
            GetResponseMatch.GetResponseMatchBuilder getResponseMatch = GetResponseMatch.builder();
            getResponseMatch.awayTeam(convertEntityToResponseTeam(matchEntity.getAwayTeamEntity()));
            getResponseMatch.homeTeam(convertEntityToResponseTeam(matchEntity.getHomeTeamEntity()));
            getResponseMatch.dateMatch(matchEntity.getDateMatch());
            getResponseMatch.homeGoals(matchEntity.getHomeGoals());
            getResponseMatch.awayGoals(matchEntity.getAwayGoals());
            return getResponseMatch.build();
        }
    }

    List<GetResponseMatch> convertMatchDtoToResponseList(List<MatchDTO> matchDTO);


    List<MatchDTO> convertMatchEntityToDto(List<MatchEntity> matchEntity);

    default GetResponseMatch matchDTOToGetResponseMatch(MatchDTO matchDTO) {
        if (matchDTO == null) {
            return null;
        } else {
            GetResponseMatch.GetResponseMatchBuilder getResponseMatch = GetResponseMatch.builder();
            getResponseMatch.awayTeam(convertEntityToResponseTeam(matchDTO.getAwayTeamEntity()));
            getResponseMatch.homeTeam(convertEntityToResponseTeam(matchDTO.getHomeTeamEntity()));
            getResponseMatch.dateMatch(matchDTO.getDateMatch());
            getResponseMatch.homeGoals(matchDTO.getHomeGoals());
            getResponseMatch.awayGoals(matchDTO.getAwayGoals());
            return getResponseMatch.build();
        }
    }

    List<MatchEntity> convertDtoToEntityList(List<MatchDTO> matchDTOList);
}
