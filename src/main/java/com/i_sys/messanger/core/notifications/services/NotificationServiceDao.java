package com.i_sys.messanger.core.notifications.services;

import com.i_sys.messanger.core.notifications.repositories.NotificationRepository;
import com.i_sys.messanger.core.notifications.repositories.NotificationViewRepository;
import com.i_sys.messanger.core.users.repositories.UserRepository;
import com.i_sys.messanger.data.notifications.Notification;
import com.i_sys.messanger.data.notifications.NotificationView;
import com.i_sys.messanger.web.controllers.notifications.dto.NotificationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationServiceDao {

    private static final Logger log = LoggerFactory
            .getLogger(NotificationServiceDao.class.getName());
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationViewRepository notificationViewRepository;

    public List<NotificationDto> getAllNotifications(UUID userId) {
        log.info("Call method of NotificationServiceDao: getAllNotifications("
                + userId + ")");

        List<NotificationDto> result = new ArrayList<>();

        for (Notification notification : notificationRepository.findAll()) {
            NotificationDto notificationDto = new NotificationDto();
            NotificationView notificationView = notificationViewRepository
                    .findByUserIdAndNotificationId(userId, notification.getId())
                    .orElse(null);

            notificationDto.setId(notification.getId());
            notificationDto.setRead(notificationView != null
                    && notificationView.isRead());
            notificationDto.setSender(notification.getSender());
            notificationDto.setText(notification.getText());
        }

        log.info("Method of NotificationServiceDao: " +
                "getAllNotifications(" + userId + ") successfully completed");

        return result;
    }

    public NotificationDto getNotificationById(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceDao: " +
                "getNotificationById(" + notificationId + "," + userId + ")");

        Notification notification = notificationRepository
                .findById(notificationId).orElse(null);

        if (notification == null) {
            return null;
        }

        NotificationDto result = new NotificationDto();

        NotificationView notificationView = notificationViewRepository
                .findByUserIdAndNotificationId(userId, notification.getId())
                .orElse(null);

        result.setId(notification.getId());
        result.setRead(notificationView != null
                && notificationView.isRead());
        result.setSender(notification.getSender());
        result.setText(notification.getText());

        log.info("Method of NotificationServiceDao: " +
                "getNotificationById(" + notificationId + "," + userId + ") " +
                "successfully completed");

        return result;
    }

    public NotificationDto createNotification(NotificationDto notification) {
        log.info("Call method of NotificationServiceDao: " +
                "createNotification(" + notification + ")");

        Notification notificationForCreate = new Notification();

        notificationForCreate.setSender(notification.getSender());
        notificationForCreate.setText(notification.getText());

        Notification entity = notificationRepository.save(notificationForCreate);

        NotificationDto result = new NotificationDto();

        result.setId(entity.getId());
        result.setSender(entity.getSender());
        result.setText(entity.getText());

        log.info("Method of NotificationServiceDao: " +
                "createNotification(" + notification + ") successfully completed");

        return result;
    }

    public NotificationDto updateNotification(UUID id, NotificationDto notification) {
        log.info("Call method of NotificationServiceDao: " +
                "updateNotification(" + id + "," + notification + ")");

        Notification notificationForUpdate = notificationRepository
                .findById(id).orElse(null);

        notificationForUpdate.setSender(notification.getSender());
        notificationForUpdate.setText(notification.getText());

        Notification entity = notificationRepository.save(notificationForUpdate);

        NotificationDto result = new NotificationDto();

        result.setId(entity.getId());
        result.setSender(entity.getSender());
        result.setText(entity.getText());

        log.info("Method of NotificationServiceDao: " +
                "updateNotification(" + id + "," + notification + ") " +
                "successfully completed");

        return result;
    }

    public void deleteNotification(UUID id) {
        log.info("Call method of NotificationServiceDao: " +
                "deleteNotification(" + id + ")");

        notificationRepository.delete(notificationRepository
                .findById(id).orElse(null));

        log.info("Method of NotificationServiceDao: " +
                "deleteNotification(" + id + ") successfully completed");
    }

    private void changeNotificationReadFlag(UUID notificationId, UUID userId,
                                            boolean readFlag) {
        NotificationView notificationView = notificationViewRepository
                .findByUserIdAndNotificationId(userId, notificationId)
                .orElse(null);

        if (notificationView == null) {
            notificationView = new NotificationView();
        }

        NotificationView newNotificationView = new NotificationView();

        newNotificationView.setNotification(
                notificationRepository.findById(notificationId)
                        .orElse(null));

        newNotificationView.setUser(
                userRepository.findById(userId)
                        .orElse(null));

        newNotificationView.setRead(readFlag);

        notificationViewRepository.save(notificationView);
    }

    public void doRead(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceDao: doRead(" + notificationId + ","
                + userId + ")");

        changeNotificationReadFlag(notificationId, userId, true);

        log.info("Method of NotificationServiceDao: doRead(" + notificationId + ","
                + userId + ") successfully completed");
    }

    public void doUnRead(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceDao: doUnRead(" + notificationId + ","
                + userId + ")");

        changeNotificationReadFlag(notificationId, userId, false);

        log.info("Method of NotificationServiceDao: doUnRead(" + notificationId + ","
                + userId + ") successfully completed");
    }
}
