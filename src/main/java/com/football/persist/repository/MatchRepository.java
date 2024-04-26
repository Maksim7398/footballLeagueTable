package com.football.persist.repository;

import com.football.persist.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, UUID> {

    @Query("from MatchEntity m " +
            "left join fetch m.awayTeam " +
            "left join fetch m.homeTeam")
    List<MatchEntity> findAllByFetch();

    @Query(value = "select CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS BIT) FROM match WHERE" +
            " date_match between (select min(date_match) from match) and :localDateTime",nativeQuery = true)
    Boolean matchByBetweenTime(@Param("localDateTime") LocalDateTime localDateTime);

    @Query("from MatchEntity m " +
            "left join TeamEntity t on t.id = m.homeTeam.id or t.id = m.awayTeam.id" +
            " where m.awayTeam.id = :teamId or m.homeTeam.id = :teamId ")
    List<MatchEntity> findAllMatchByTeam(@Param("teamId") UUID teamId);
}
