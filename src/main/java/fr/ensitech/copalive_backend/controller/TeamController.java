package fr.ensitech.copalive_backend.controller;

import fr.ensitech.copalive_backend.entity.Team;
import fr.ensitech.copalive_backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Integer id) {
        return teamRepository.findById(id).orElse(null);
    }
}