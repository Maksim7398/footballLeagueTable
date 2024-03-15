package com.football.persist.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TeamDTO {

    private UUID id;

    private String name;

    private Integer points = 0;

    private int totalGoals;

    private int scipGoals;
}
