package com.football.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTeamRequest {

    private String name;

}
