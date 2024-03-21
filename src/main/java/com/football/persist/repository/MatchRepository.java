package com.football.persist.repository;

import com.football.persist.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, UUID> {

    @Query("from MatchEntity m " +
            "left join fetch m.awayTeamEntity " +
            "left join fetch m.homeTeamEntity")
    List<MatchEntity> findAllByFetch();
}
