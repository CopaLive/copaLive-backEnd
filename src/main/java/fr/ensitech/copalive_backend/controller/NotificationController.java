package fr.ensitech.copalive_backend.controller;

import fr.ensitech.copalive_backend.entity.Notification;
import fr.ensitech.copalive_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public List<Notification> getUnreadNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}
