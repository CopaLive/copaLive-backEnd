package fr.ensitech.copalive_backend.controller;

import fr.ensitech.copalive_backend.entity.*;
import fr.ensitech.copalive_backend.repository.MatchRepository;
import fr.ensitech.copalive_backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/games")
    public Map<String, List<Map<String, Object>>> getAllGames() {
        List<Match> matches = matchRepository.findAll();
        List<Map<String, Object>> formattedGames = matches.stream()
                .map(this::convertToFrontEndFormat)
                .collect(Collectors.toList());
        return Collections.singletonMap("games", formattedGames);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Map<String, Object>> getGameById(@PathVariable Integer id) {
        return matchRepository.findById(id)
                .map(match -> ResponseEntity.ok(convertToFrontEndFormat(match)))
                .orElse(ResponseEntity.notFound().build());
    }

    private Map<String, Object> convertToFrontEndFormat(Match match) {
        Map<String, Object> gameMap = new HashMap<>();
        gameMap.put("id", match.getId());
        gameMap.put("stage", match.getStage());
        gameMap.put("status", "TIMED".equals(match.getStatus()) ? "SCHEDULED" : match.getStatus());
        gameMap.put("date", match.getUtcDate() != null ? match.getUtcDate().toString() : null);
        gameMap.put("homeScore", match.getHomeScore() != null ? match.getHomeScore() : 0);
        gameMap.put("awayScore", match.getAwayScore() != null ? match.getAwayScore() : 0);

        gameMap.put("homeTeam", mapTeamForFront(match.getHomeTeam(), match.getHomeScore()));
        gameMap.put("awayTeam", mapTeamForFront(match.getAwayTeam(), match.getAwayScore()));

        // Récupération de tous les événements du match
        List<Event> allEvents = eventRepository.findByMatchId(match.getId());

        // Création de la liste unifiée que le composant EventList.tsx parcourt
        List<Map<String, Object>> eventsFormatted = allEvents.stream()
                .map(this::mapSingleEvent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // On injecte la liste unifiée dans "events"
        gameMap.put("events", eventsFormatted);

        // On garde aussi les listes séparées par sécurité pour les autres composants
        gameMap.put("goals", eventsFormatted.stream().filter(e -> "GOAL".equals(e.get("type"))).collect(Collectors.toList()));
        gameMap.put("bookings", eventsFormatted.stream().filter(e -> "BOOKING".equals(e.get("type"))).collect(Collectors.toList()));
        gameMap.put("substitutions", new ArrayList<>());
        gameMap.put("penalties", new ArrayList<>());

        return gameMap;
    }

    private Map<String, Object> mapSingleEvent(Event e) {
        Map<String, Object> eventMap = new HashMap<>();

        if (e instanceof Goal) {
            Goal g = (Goal) e;
            eventMap.put("type", "GOAL");
            Map<String, Object> goalData = new HashMap<>();
            goalData.put("minute", g.getOccurredAt());

            // Structure: goal.scorer.name
            Map<String, Object> scorerObj = new HashMap<>();
            scorerObj.put("name", g.getScorer() != null ? g.getScorer().getFirstName() + " " + g.getScorer().getLastName() : "Buteur");
            goalData.put("scorer", scorerObj);

            // Structure: goal.team.name
            goalData.put("team", mapTeamShort(g.getScorer() != null ? g.getScorer().getCurrentTeam() : null));

            eventMap.put("goal", goalData);
        }
        else if (e instanceof Booking) {
            Booking b = (Booking) e;
            eventMap.put("type", "BOOKING");
            Map<String, Object> bookingData = new HashMap<>();
            bookingData.put("minute", b.getOccurredAt());
            bookingData.put("card", b.getCardType()); // YELLOW_CARD / RED_CARD

            // Structure: booking.player.name
            Map<String, Object> playerObj = new HashMap<>();
            playerObj.put("name", b.getReceiver() != null ? b.getReceiver().getFirstName() + " " + b.getReceiver().getLastName() : "Joueur");
            bookingData.put("player", playerObj);

            // Structure: booking.team.name
            bookingData.put("team", mapTeamShort(b.getReceiver() != null ? b.getReceiver().getCurrentTeam() : null));

            eventMap.put("booking", bookingData);
        } else {
            return null;
        }

        return eventMap;
    }

    private Map<String, Object> mapTeamShort(Team team) {
        Map<String, Object> teamMap = new HashMap<>();
        teamMap.put("name", team != null ? team.getName() : "N/A");
        teamMap.put("id", team != null ? team.getId() : null);
        return teamMap;
    }

    private Map<String, Object> mapTeamForFront(Team team, Integer score) {
        if (team == null) return null;
        Map<String, Object> teamMap = new HashMap<>();
        teamMap.put("id", team.getId());
        teamMap.put("name", team.getName());
        teamMap.put("shortName", team.getShortName());
        teamMap.put("flag", team.getCrest());
        teamMap.put("score", score != null ? score : 0);
        return teamMap;
    }
}