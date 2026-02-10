package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "utc_date")
    private LocalDateTime utcDate;

    // SCHEDULED, IN_PLAY, PAUSED, FINISHED
    private String status;

    // PHASES : "GROUP_STAGE", "LAST_32", "LAST_16", "QUARTER_FINALS",
    //          "SEMI_FINALS", "THIRD_PLACE", "FINAL"
    private String stage;

    // Rempli uniquement pour les matchs de poule (ex: "Group A")
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "score_home")
    private Integer scoreHome = 0;

    @Column(name = "score_away")
    private Integer scoreAway = 0;

    @Column(name = "match_time")
    private String matchTime;      // ex: "90+4'"

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;
}
