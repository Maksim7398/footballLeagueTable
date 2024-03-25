package com.football.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private UUID id;

    @Nullable
    private String name;

    private Integer points;

    private Integer otherPoints;

    private int numberOfGames;

    private int totalGoals;

    private int scipGoals;
}
