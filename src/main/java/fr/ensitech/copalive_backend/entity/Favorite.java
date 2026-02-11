package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "team_id"}),
                @UniqueConstraint(columnNames = {"user_id", "match_id"}),
                @UniqueConstraint(columnNames = {"user_id", "area_id"})
        })
@Data @NoArgsConstructor @AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L'utilisateur qui a mis le like
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Type de la cible : "TEAM", "MATCH", "AREA" (utile pour le frontend)
    @Column(name = "target_type", nullable = false)
    private String targetType;

    // --- LES 3 RELATIONS OPTIONNELLES ---

    // Relation "EquipesFavorites"
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Relation "MatchsFavoris"
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    // Relation "LieuxFavoris"
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}