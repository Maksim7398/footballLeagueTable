package com.football.persist.repository;

import com.football.persist.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Long> {

    boolean existsByName(String name);

    Optional<Tournament> findByName(String name);
}
