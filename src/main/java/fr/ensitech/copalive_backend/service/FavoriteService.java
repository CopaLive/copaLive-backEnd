package fr.ensitech.copalive_backend.service;

import fr.ensitech.copalive_backend.entity.*;
import fr.ensitech.copalive_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchRepository matchRepository;

    public Favorite addFavorite(Long userId, String type, Integer targetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));

        Favorite fav = new Favorite();
        fav.setUser(user);
        fav.setTargetType(type);

        if ("TEAM".equalsIgnoreCase(type)) {
            if (favoriteRepository.existsByUserIdAndTeamId(userId, targetId)) {
                throw new RuntimeException("Cette équipe est déjà dans vos favoris");
            }
            Team team = teamRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("Équipe introuvable"));
            fav.setTeam(team);
            fav.setMatch(null);

        } else if ("MATCH".equalsIgnoreCase(type)) {
            if (favoriteRepository.existsByUserIdAndMatchId(userId, targetId)) {
                throw new RuntimeException("Ce match est déjà dans vos favoris");
            }
            Match match = matchRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("Match introuvable"));
            fav.setMatch(match);
            fav.setTeam(null);

        } else {
            throw new RuntimeException("Type de favori non supporté : " + type);
        }

        return favoriteRepository.save(fav);
    }

    @Transactional
    public void removeFavorite(Long userId, String type, Integer targetId) {
        if ("TEAM".equalsIgnoreCase(type)) {
            favoriteRepository.deleteByUserIdAndTeamId(userId, targetId);
        } else if ("MATCH".equalsIgnoreCase(type)) {
            favoriteRepository.deleteByUserIdAndMatchId(userId, targetId);
        }
    }

    public List<Favorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}
