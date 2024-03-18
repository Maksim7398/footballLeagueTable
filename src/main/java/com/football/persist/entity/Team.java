package com.football.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "team")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team implements Comparable<Team>{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "points")
    private Integer points;

    @Column(name = "other_points")
    private Integer otherPoints;

    @Column(name = "total_goals")
    private int totalGoals;

    @Column(name = "scip_goals")
    private int scipGoals;

    @Column(name = "number_of_games")
    private int numberOfGames;

    public void setTotalGoals(int totalGoals) {
        this.totalGoals += totalGoals;
    }

    public void setCountPoints(Integer countPoints) {
        this.points += countPoints;
    }

    public void setScipGoals(int scipGoals) {
        this.scipGoals += scipGoals;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames += numberOfGames;
    }

    public void setOtherPoints(Integer otherPoints) {
        this.otherPoints += otherPoints;
    }

    @Override
    public int compareTo(Team o) {
        return this.getOtherPoints().compareTo(o.getOtherPoints());
    }
}
