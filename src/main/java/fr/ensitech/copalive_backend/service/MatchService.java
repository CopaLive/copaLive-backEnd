package fr.ensitech.copalive_backend.service;

import fr.ensitech.copalive_backend.entity.Event;
import fr.ensitech.copalive_backend.entity.Match;
import fr.ensitech.copalive_backend.repository.EventRepository;
import fr.ensitech.copalive_backend.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Integer id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match introuvable"));
    }

    public List<Match> getMatchesByStage(String stage) {
        return matchRepository.findByStage(stage);
    }

    public List<Event> getMatchEvents(Integer matchId) {
        return eventRepository.findByMatchId(matchId);
    }
}
