package fr.ensitech.copalive_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "match_date")
    private LocalDateTime utcDate;

    @JsonProperty("date")
    public String getFormattedDate() {
        return utcDate != null ? utcDate.toString() : null;
    }

    private String status; // SCHEDULED, IN_PLAY, PAUSED, FINISHED
    private String stage;
    private String duration;

    // --- NOUVEAUX CHAMPS ---
    @Column(name = "home_score")
    private Integer homeScore = 0; // Valeur par défaut 0

    @Column(name = "away_score")
    private Integer awayScore = 0;
    // -----------------------

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "winner_team_id")
    private Team winner;
}