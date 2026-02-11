package fr.ensitech.copalive_backend.dto;

import lombok.Data;

@Data
public class GameResponse {
    private Integer id;
    private TeamResponse homeTeam;
    private TeamResponse awayTeam;
    private String status;
    private String stage;
    private String date;

    @Data
    public static class TeamResponse {
        private Integer id;
        private String name;
        private String shortName;
        private String flag;
        private Integer score; // Le score est ici pour le Front
    }
}
