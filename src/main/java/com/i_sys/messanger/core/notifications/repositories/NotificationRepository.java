package com.i_sys.messanger.core.notifications.repositories;

import com.i_sys.messanger.data.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findNotificationsByNotificationViews_UserId(UUID userId);

    @Query(nativeQuery = true,
            value = "SELECT n.id, n.sender, n.text FROM notification.tr_notification n JOIN notification.tr_notification_view nv " +
                    "ON n.id = nv.notification_id EXCEPT SELECT n.id, n.sender, n.text FROM notification.tr_notification n " +
                    "JOIN notification.tr_notification_view nv ON n.id = nv.notification_id WHERE nv.user_id = :userId")
    List<Notification> findAllUnReadNotifications(UUID userId);
}
