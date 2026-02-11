package fr.ensitech.copalive_backend.controller;

import fr.ensitech.copalive_backend.dto.FavoriteRequest;
import fr.ensitech.copalive_backend.entity.Favorite;
import fr.ensitech.copalive_backend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteRequest req) {
        try {
            Favorite fav = favoriteService.addFavorite(req.getUserId(), req.getType(), req.getTargetId());
            return ResponseEntity.ok(fav);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeFavorite(
            @RequestParam Long userId,
            @RequestParam String type,
            @RequestParam Integer targetId) {
        try {
            favoriteService.removeFavorite(userId, type, targetId);
            return ResponseEntity.ok("Favori supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression");
        }
    }

    @GetMapping("/user/{userId}")
    public List<Favorite> getUserFavorites(@PathVariable Long userId) {
        return favoriteService.getUserFavorites(userId);
    }
}