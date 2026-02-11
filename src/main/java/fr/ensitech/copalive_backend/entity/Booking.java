package fr.ensitech.copalive_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("CARD")
@Data
@EqualsAndHashCode(callSuper = true)
public class Booking extends Event{

    @Column(name = "card_type")
    private String cardType;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Player receiver;
}
