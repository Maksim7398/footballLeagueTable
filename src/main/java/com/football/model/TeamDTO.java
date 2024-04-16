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
public class TeamDTO implements Comparable<TeamDTO> {

    private UUID id;

    @Nullable
    private String name;

    @Nullable
    private String country;

    @Nullable
    private String city;

    private int points;

    private int otherPoints;

    private int numberOfGames;

    private int totalGoals;

    private int scipGoals;

    @Override
    public int compareTo(TeamDTO o) {
        if (this.getOtherPoints() == o.getOtherPoints() && this.getPoints() == o.getPoints()) {
            return Integer.compare(this.getTotalGoals(), o.getTotalGoals());
        }
        if (this.getPoints() == o.getPoints()) {
            return Integer.compare(this.getOtherPoints(), o.getOtherPoints());
        } else {
            return Integer.compare(this.getPoints(), o.getPoints());
        }
    }
}
