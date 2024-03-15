package com.football.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "match")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "home_team")
    private Team homeTeam;

    @OneToOne
    @JoinColumn(name = "away_team")
    private Team awayTeam;

    @Column(name = "time")
    private LocalDateTime date_match;

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Column(name = "away_goals")
    private Integer awayGoals;

    public void result(Team team1, Team team2, int homeGoals, int awayGoals){
        this.setAwayTeam(team1);
        this.setHomeTeam(team2);
        this.setDate_match(LocalDateTime.now());
        this.setHomeGoals(homeGoals);
        this.setAwayGoals(awayGoals);
        if (homeGoals < awayGoals){
            team1.setCountPoints(3);
        }
        if (homeGoals > awayGoals) {
            team2.setCountPoints(3);
        }
        if(homeGoals == awayGoals) {
            team1.setCountPoints(1);
            team2.setCountPoints(1);
        }
        team2.setScipGoals(awayGoals);
        team1.setScipGoals(homeGoals);
        team2.setTotalGoals(homeGoals);
        team1.setTotalGoals(awayGoals);
    }
}
