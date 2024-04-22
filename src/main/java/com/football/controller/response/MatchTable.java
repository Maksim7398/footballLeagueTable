package com.football.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class MatchTable {

    private String calculateDate;

    private String tournamentName;

    private List<GetResponseMatch> matchList;
}
