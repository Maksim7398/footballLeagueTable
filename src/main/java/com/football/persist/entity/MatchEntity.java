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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Table(name = "match")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity implements Comparable<MatchEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "home_team")
    private TeamEntity homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team")
    private TeamEntity awayTeam;

    @Column(name = "date_match")
    private LocalDateTime dateMatch;

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Column(name = "away_goals")
    private Integer awayGoals;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "tournament_id")
    private Tournament tournament;

    @Override
    public int compareTo(MatchEntity o) {
        return this.dateMatch.compareTo(o.dateMatch);
    }
}
