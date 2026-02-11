package fr.ensitech.copalive_backend.repository;

import fr.ensitech.copalive_backend.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Optional<Team> findByName(String name);
    Optional<Team> findByShortName(String shortName);
    List<Team> findByGroupName(String groupName);
    List<Team> findByNameContainingIgnoreCase(String nameFragment);
}
