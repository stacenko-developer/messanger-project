package com.i_sys.messanger.dao.notification.repository;

import com.i_sys.messanger.dao.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findNotificationsByNotificationViews_UserId(UUID userId, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT n_first.id, n_first.sender, n_first.text FROM notification.tr_notification n_first " +
                    "JOIN notification.tr_notification_view nv ON n_first.id = nv.notification_id " +
                    "WHERE NOT EXISTS " +
                    "(SELECT n_second.id FROM notification.tr_notification n_second " +
                    "JOIN notification.tr_notification_view nv ON n_second.id = nv.notification_id " +
                    "WHERE nv.user_id = :userId AND n_second.id = n_first.id)")
    List<Notification> findAllUnReadNotifications(UUID userId, Pageable pageable);
}
