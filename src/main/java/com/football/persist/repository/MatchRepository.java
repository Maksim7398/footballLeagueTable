package com.football.persist.repository;

import com.football.persist.entity.MatchEntity;
import com.football.persist.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, UUID> {

    @Query("from MatchEntity m " +
            "left join fetch m.awayTeam " +
            "left join fetch m.homeTeam")
    List<MatchEntity> findAllByFetch();

    @Query("from MatchEntity " +
            "where homeTeam = :homeTeam")
    List<MatchEntity> findMatchByHomeTeam(@Param("homeTeam") TeamEntity homeTeam);

    @Query("from MatchEntity " +
            "where awayTeam = :awayTeam")
    List<MatchEntity> findMatchByAwayTeam(@Param("awayTeam") TeamEntity awayTeam);
}
