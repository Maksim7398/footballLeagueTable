package com.football.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetResponseTeamForMatch {
    private String name;
}
