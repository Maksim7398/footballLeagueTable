package com.football.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "match")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "home_team")
    private TeamEntity homeTeamEntity;

    @ManyToOne
    @JoinColumn(name = "away_team")
    private TeamEntity awayTeamEntity;

    @Column(name = "time")
    private LocalDateTime dateMatch;

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Column(name = "away_goals")
    private Integer awayGoals;
}