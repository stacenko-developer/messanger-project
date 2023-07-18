package com.i_sys.messanger.dao.notification.repository;

import com.i_sys.messanger.dao.notification.entity.NotificationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationViewRepository extends JpaRepository<NotificationView, UUID> {
    Optional<NotificationView> findByUserIdAndNotificationId(UUID userId, UUID notificationId);
}
