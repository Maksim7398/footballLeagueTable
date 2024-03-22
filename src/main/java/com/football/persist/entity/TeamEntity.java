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
public class TeamEntity implements Comparable<TeamEntity>{

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
    private Integer totalGoals;

    @Column(name = "scip_goals")
    private int scipGoals;

    @Column(name = "number_of_games")
    private int numberOfGames;

    @Override
    public int compareTo(TeamEntity o) {
        if (this.getOtherPoints().equals(o.getOtherPoints()) && this.getPoints().equals(o.getPoints())){
            return this.getTotalGoals().compareTo(o.getTotalGoals());
        }
        if (this.getPoints().equals(o.getPoints())){
            return this.getOtherPoints().compareTo(o.getOtherPoints());
        }
        else {
            return this.getPoints().compareTo(o.getPoints());
        }
    }
}
