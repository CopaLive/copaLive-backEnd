package fr.ensitech.copalive_backend.entity;

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

    @Column(name = "utc_date")
    private LocalDateTime utcDate;

    private String status;
    private String stage;
    private String duration;

    // Relation "Localiser" (1,1)
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    // Relations Domicile / Extérieur
    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;

    // Relation "Gagner" (0,1) - Le vainqueur
    @ManyToOne
    @JoinColumn(name = "winner_team_id")
    private Team winner;
}