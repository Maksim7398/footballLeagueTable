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
    private Integer points = 0;
    @Column(name = "total_goals")
    private int totalGoals;
    @Column(name = "scip_goals")
    private int scipGoals;

    public void setTotalGoals(int totalGoals) {
        this.totalGoals += totalGoals;
    }

    public void setCountPoints(Integer countPoints) {
        this.points += countPoints;
    }

    public void setScipGoals(int scipGoals) {
        this.scipGoals += scipGoals;
    }

    @Override
    public int compareTo(Team o) {
        return this.getPoints().compareTo(o.getPoints());
    }
}
