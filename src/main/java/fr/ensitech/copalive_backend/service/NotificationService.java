package fr.ensitech.copalive_backend.service;

import fr.ensitech.copalive_backend.entity.Notification;
import fr.ensitech.copalive_backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    public void markAsRead(Long notificationId) {
        Notification notif = notificationRepository.findById(notificationId).orElse(null);
        if (notif != null) {
            notif.setRead(true);
            notificationRepository.save(notif);
        }
    }
}
