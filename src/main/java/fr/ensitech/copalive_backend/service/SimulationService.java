package fr.ensitech.copalive_backend.service;

import fr.ensitech.copalive_backend.entity.*;
import fr.ensitech.copalive_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class SimulationService {

    @Autowired private MatchRepository matchRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private PlayerRepository playerRepository;

    private final Random random = new Random();

    // S'exécute toutes les 10 secondes
    @Scheduled(fixedRate = 2500)
    @Transactional
    public void simulateLiveMatches() {
        // 1. Récupérer les matchs en cours
        // Note: Vous pouvez changer manuellement le statut d'un match à 'IN_PLAY' en base pour tester
        List<Match> liveMatches = matchRepository.findAll().stream()
                .filter(m -> "IN_PLAY".equals(m.getStatus()))
                .toList();

        if (liveMatches.isEmpty()) {
            System.out.println("⏳ Aucune simulation : pas de match en cours (IN_PLAY).");
            return;
        }

        System.out.println("⚽ Simulation en cours pour " + liveMatches.size() + " match(s)...");

        for (Match match : liveMatches) {
            // 30% de chance qu'il se passe quelque chose à chaque cycle
            if (random.nextInt(100) < 30) {
                generateRandomEvent(match);
            }
        }
    }

    private void generateRandomEvent(Match match) {
        int eventType = random.nextInt(3); // 0 = But, 1 = Carton, 2 = Rien

        // Récupérer les joueurs pour l'événement
        List<Player> homePlayers = playerRepository.findByCurrentTeamId(match.getHomeTeam().getId());
        List<Player> awayPlayers = playerRepository.findByCurrentTeamId(match.getAwayTeam().getId());

        if (homePlayers.isEmpty() || awayPlayers.isEmpty()) return;

        // Décider quelle équipe fait l'action
        boolean isHomeAction = random.nextBoolean();
        List<Player> activeTeamPlayers = isHomeAction ? homePlayers : awayPlayers;
        Player randomPlayer = activeTeamPlayers.get(random.nextInt(activeTeamPlayers.size()));

        // Simuler le temps (juste un timestamp actuel pour l'exemple)
        String time = random.nextInt(90) + "'";

        if (eventType == 0) { // BUT !!! ⚽
            Goal goal = new Goal();
            goal.setMatch(match);
            goal.setScorer(randomPlayer);
            goal.setOccurredAt(time);
            goal.setType("NORMAL");
            eventRepository.save(goal);

            // Mettre à jour le score
            if (isHomeAction) {
                match.setHomeScore(match.getHomeScore() + 1);
            } else {
                match.setAwayScore(match.getAwayScore() + 1);
            }
            matchRepository.save(match);
            System.out.println("GOAL! " + match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName());

        } else if (eventType == 1) { // CARTON 🟨
            Booking booking = new Booking();
            booking.setMatch(match);
            booking.setReceiver(randomPlayer);
            booking.setOccurredAt(time);
            booking.setCardType(random.nextBoolean() ? "YELLOW_CARD" : "RED_CARD");
            eventRepository.save(booking);
            System.out.println("Carton pour " + randomPlayer.getLastName());
        }
    }
}