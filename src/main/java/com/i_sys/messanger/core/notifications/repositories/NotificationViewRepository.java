package com.i_sys.messanger.core.notifications.repositories;

import com.i_sys.messanger.data.notifications.NotificationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationViewRepository extends JpaRepository<NotificationView, UUID> {
    Optional<NotificationView> findByUserIdAndNotificationId(UUID userId, UUID notificationId);
}
