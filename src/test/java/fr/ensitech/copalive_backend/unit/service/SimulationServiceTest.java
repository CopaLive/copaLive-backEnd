package fr.ensitech.copalive_backend.unit.service;

import fr.ensitech.copalive_backend.entity.Match;
import fr.ensitech.copalive_backend.repository.MatchRepository;
import fr.ensitech.copalive_backend.service.SimulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Ce test vérifie que lorsqu'un but est marqué par l'équipe à domicile,
 * le score du match est correctement mis à jour en base de données.
 *
 * On utilise Mockito pour mocker dans un client serveur mocké
 * On utilise JUnit5 (@Test) vérifie les insertions
 * **/

class SimulationServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private SimulationService simulationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldIncrementHomeScoreWhenHomeTeamScores() {
        // GIVEN : Un match avec un score de 0-0
        Match match = new Match();
        match.setId(1);
        match.setHomeScore(0);
        match.setAwayScore(0);

        // WHEN : On simule un but pour l'équipe à domicile (isHome = true)
        // Note: Assure-toi que cette méthode est 'public' ou 'protected' dans ton SimulationService
        simulationService.updateMatchScore(match, true);

        // THEN : On vérifie que le repository a sauvegardé le match avec le score 1-0
        ArgumentCaptor<Match> matchCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(matchCaptor.capture());

        Match savedMatch = matchCaptor.getValue();
        assertEquals(1, savedMatch.getHomeScore(), "Le score à domicile devrait être de 1");
        assertEquals(0, savedMatch.getAwayScore(), "Le score à l'extérieur devrait rester à 0");
    }

    @Test
    void shouldIncrementAwayScoreWhenAwayTeamScores() {
        // GIVEN
        Match match = new Match();
        match.setId(2);
        match.setHomeScore(1);
        match.setAwayScore(1);

        // WHEN : But pour l'équipe visiteuse (isHome = false)
        simulationService.updateMatchScore(match, false);

        // THEN
        ArgumentCaptor<Match> matchCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository).save(matchCaptor.capture());

        Match savedMatch = matchCaptor.getValue();
        assertEquals(1, savedMatch.getHomeScore());
        assertEquals(2, savedMatch.getAwayScore(), "Le score à l'extérieur devrait être de 2");
    }
}
