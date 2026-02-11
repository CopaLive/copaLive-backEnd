package fr.ensitech.copalive_backend.repository;

import fr.ensitech.copalive_backend.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findByStage(String stage);
    List<Match> findByAreaId(Integer areaId);
}
