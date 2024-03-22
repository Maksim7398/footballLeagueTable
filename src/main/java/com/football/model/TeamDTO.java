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

    private Integer points = 0;

    private Integer otherPoints = 0;

    private int numberOfGames;

    private int totalGoals;

    private int scipGoals;

    public void setTotalGoals(int totalGoals) {
        this.totalGoals += totalGoals;
    }

    public void setCountPoints(Integer countPoints) {
        if (this.points == null){
            this.points = 1;
        }
        this.points += countPoints;
    }

    public void setScipGoals(int scipGoals) {
        this.scipGoals += scipGoals;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames += numberOfGames;
    }

    public void setOtherPoints(Integer otherPoints) {
        if (this.otherPoints == null){
            this.otherPoints = 1;
        }
        this.otherPoints += otherPoints;
    }
}
