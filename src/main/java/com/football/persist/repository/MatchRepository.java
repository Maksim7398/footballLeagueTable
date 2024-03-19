package com.football.persist.repository;

import com.football.persist.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {

    @Query("from Match m " +
            "left join fetch m.awayTeam " +
            "left join fetch m.homeTeam")
    List<Match> findAllByFetch();
}
