package fr.ensitech.copalive_backend.dto;

import lombok.Data;

@Data
public class FavoriteRequest {
    private Long userId;

    private String type;

    private Integer targetId;
}
