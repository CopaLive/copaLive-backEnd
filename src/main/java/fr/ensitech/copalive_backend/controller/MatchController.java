package fr.ensitech.copalive_backend.controller;

import fr.ensitech.copalive_backend.entity.Event;
import fr.ensitech.copalive_backend.entity.Match;
import fr.ensitech.copalive_backend.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin(origins = "*")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable Integer id) {
        return matchService.getMatchById(id);
    }

    @GetMapping("/stage/{stageName}")
    public List<Match> getMatchesByStage(@PathVariable String stageName) {
        return matchService.getMatchesByStage(stageName);
    }

    @GetMapping("/{id}/events")
    public List<Event> getMatchEvents(@PathVariable Integer id) {
        return matchService.getMatchEvents(id);
    }
}
