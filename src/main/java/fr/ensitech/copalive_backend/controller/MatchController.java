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

        // Récupération sécurisée des événements
        List<Event> events = eventRepository.findByMatchId(match.getId());

        // On fusionne tous les événements dans une seule liste "events" si le front le demande,
        // mais ici on suit votre structure actuelle avec des listes séparées
        gameMap.put("goals", events.stream().filter(e -> e instanceof Goal).map(this::formatGoal).collect(Collectors.toList()));
        gameMap.put("bookings", events.stream().filter(e -> e instanceof Booking).map(this::formatBooking).collect(Collectors.toList()));
        gameMap.put("substitutions", new ArrayList<>());
        gameMap.put("penalties", new ArrayList<>());

        return gameMap;
    }

    private Map<String, Object> formatGoal(Event e) {
        Goal g = (Goal) e;
        Map<String, Object> eventObj = new HashMap<>();
        eventObj.put("type", "GOAL"); //

        Map<String, Object> goalData = new HashMap<>();
        goalData.put("minute", g.getOccurredAt()); //

        // Structure scorer.name
        Map<String, Object> scorerObj = new HashMap<>();
        String fullName = "Joueur inconnu";
        if (g.getScorer() != null) {
            fullName = g.getScorer().getFirstName() + " " + g.getScorer().getLastName();
        }
        scorerObj.put("name", fullName);
        goalData.put("scorer", scorerObj);

        // Structure team.name pour getEventTeam
        Map<String, Object> teamData = new HashMap<>();
        teamData.put("name", (g.getScorer() != null && g.getScorer().getCurrentTeam() != null)
                ? g.getScorer().getCurrentTeam().getName() : "N/A");
        goalData.put("team", teamData);

        eventObj.put("goal", goalData);
        return eventObj;
    }

    private Map<String, Object> formatBooking(Event e) {
        Booking b = (Booking) e;
        Map<String, Object> eventObj = new HashMap<>();
        eventObj.put("type", "BOOKING"); //

        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("minute", b.getOccurredAt()); //
        bookingData.put("card", b.getCardType()); //

        // Structure player.name
        Map<String, Object> playerObj = new HashMap<>();
        String fullName = "Joueur inconnu";
        if (b.getReceiver() != null) {
            fullName = b.getReceiver().getFirstName() + " " + b.getReceiver().getLastName();
        }
        playerObj.put("name", fullName);
        bookingData.put("player", playerObj);

        // Structure team.name
        Map<String, Object> teamData = new HashMap<>();
        teamData.put("name", (b.getReceiver() != null && b.getReceiver().getCurrentTeam() != null)
                ? b.getReceiver().getCurrentTeam().getName() : "N/A");
        bookingData.put("team", teamData);

        eventObj.put("booking", bookingData);
        return eventObj;
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