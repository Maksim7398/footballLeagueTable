package com.football.persist.repository;

import com.football.persist.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {

    @Query(
            " from TeamEntity t " +
                    " join MatchEntity m on m.homeTeam.id = t.id or m.awayTeam.id = t.id " +
                    " where m.tournament.id = :tournamentId ")
    List<TeamEntity> findAllByTournamentID(Long tournamentId);

    Optional<TeamEntity> findTeamEntityByName(String name);
}
