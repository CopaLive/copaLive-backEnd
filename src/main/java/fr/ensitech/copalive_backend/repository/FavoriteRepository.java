package fr.ensitech.copalive_backend.repository;

import fr.ensitech.copalive_backend.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);

    boolean existsByUserIdAndTeamId(Long userId, Integer teamId);
    boolean existsByUserIdAndMatchId(Long userId, Integer matchId);

    void deleteByUserIdAndTeamId(Long userId, Integer teamId);
    void deleteByUserIdAndMatchId(Long userId, Integer matchId);
}
