-- ============================================================
-- MATCHS DE LA COUPE DU MONDE 2026 (Mode LIVE ACTIVÉ)
-- ============================================================

-- 1. MATCHS TERMINÉS (Historique)
-- France bat Danemark (2-1)
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES ('2026-06-11 21:00:00', 'FINISHED', 'GROUP_STAGE', 'REGULAR', 33, 4, 33, 1, 2, 1);

-- Brésil bat Mexique (3-0)
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES ('2026-06-12 18:00:00', 'FINISHED', 'GROUP_STAGE', 'REGULAR', 9, 1, 9, 1, 3, 0);

-- Argentine bat Afrique du Sud (1-0)
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES ('2026-06-13 15:00:00', 'FINISHED', 'GROUP_STAGE', 'REGULAR', 37, 2, 37, 1, 1, 0);

-- Angleterre vs Portugal (Match Nul 1-1)
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES ('2026-06-14 21:00:00', 'FINISHED', 'GROUP_STAGE', 'REGULAR', 45, 41, NULL, 1, 1, 1);


-- 2. MATCHS EN DIRECT (Simulation active dès le démarrage !) 🔥

-- France vs Brésil (Le choc !) - ID 5
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES (NOW(), 'IN_PLAY', 'GROUP_STAGE', 'REGULAR', 33, 9, NULL, 1, 0, 0);

-- Portugal vs Corée du Sud - ID 6
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES (NOW(), 'IN_PLAY', 'GROUP_STAGE', 'REGULAR', 41, 3, NULL, 1, 0, 0);


-- 3. MATCHS À VENIR (Futur)

-- Argentine vs Mexique
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES ('2026-06-21 15:00:00', 'TIMED', 'GROUP_STAGE', NULL, 37, 1, NULL, 1, 0, 0);

-- 1/8 de Finale : France vs Angleterre
INSERT INTO matches (match_date, status, stage, duration, home_team_id, away_team_id, winner_team_id, area_id, home_score, away_score)
VALUES ('2026-06-30 21:00:00', 'SCHEDULED', 'LAST_16', NULL, 33, 45, NULL, 1, 0, 0);