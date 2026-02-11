package fr.ensitech.copalive_backend.integration.controller;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Ce test d'intégration lance le contexte Spring complet et utilise MockMvc pour simuler des appels HTTP réels sur vos API.
 * Il vérifie que le Controller, le Service et le Repository fonctionnent ensemble.
 *
 * On utilise SpringBootTest pour charger le contexte de l'application
 * */

@SpringBootTest
@AutoConfigureMockMvc
public class MatchControllerIntegration {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnMatchesListFromApi() throws Exception {
        mockMvc.perform(get("/api/games")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                // Vérifie que la structure JSON contient bien une clé "games"
                .andExpect(jsonPath("$.games", hasSize(greaterThanOrEqualTo(0))));
    }

    // Retourne un erreur 404 pour un match inexistant
    @Test
    void shouldReturn404ForInvalidMatchId() throws Exception {
        mockMvc.perform(get("/api/game/9999"))
                .andExpect(status().isNotFound());
    }
}
