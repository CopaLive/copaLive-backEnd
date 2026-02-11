package fr.ensitech.copalive_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// src/main/java/fr/ensitech/copalive_backend/entity/Team.java
@Data
@Entity
@Table(name = "teams")
public class Team {
    @Id
    private Integer id;

    private String name;

    @Column(name = "short_name")
    private String shortName;

    @JsonProperty("flag")
    private String crest;

    @Column(name = "group_name")
    private String groupName;
}