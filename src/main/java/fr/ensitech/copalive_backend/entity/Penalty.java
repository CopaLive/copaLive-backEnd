package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Penalty extends Event{

    @ManyToOne
    @JoinColumn(name = "shooter_id")
    private Player shooter;

    private boolean isScored;
}
