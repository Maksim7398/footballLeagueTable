package com.football.persist.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@Builder
public class TeamDTO {

    private UUID id;
    @Nullable
    private String name;

    private Integer points;

    private Integer otherPoints;

    private int totalGoals;

    private int scipGoals;
}
