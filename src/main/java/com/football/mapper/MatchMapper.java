package com.football.mapper;

import com.football.controller.response.GetResponseMatch;
import com.football.controller.response.GetResponseTeamForMatch;
import com.football.persist.entity.MatchEntity;
import com.football.model.MatchDTO;
import com.football.persist.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    default GetResponseTeamForMatch convertEntityToResponseTeam(TeamEntity teamEntity) {
        return Mappers.getMapper(TeamMapper.class).convertEntityToResponseTeam(teamEntity);
    }

    default MatchDTO convertEntityToDto(MatchEntity matchEntity) {
        if (matchEntity == null) {
            return null;
        }

        MatchDTO.MatchDTOBuilder matchDTO = MatchDTO.builder();

        matchDTO.id(matchEntity.getId());
        matchDTO.homeTeamEntity(matchEntity.getHomeTeam());
        matchDTO.awayTeamEntity(matchEntity.getAwayTeam());
        matchDTO.dateMatch(matchEntity.getDateMatch());
        matchDTO.homeGoals(matchEntity.getHomeGoals());
        matchDTO.awayGoals(matchEntity.getAwayGoals());

        return matchDTO.build();
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
