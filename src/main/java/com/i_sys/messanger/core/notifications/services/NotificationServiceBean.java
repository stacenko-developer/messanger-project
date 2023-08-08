package com.i_sys.messanger.core.notifications.services;

import com.i_sys.messanger.web.controllers.notifications.dto.NotificationDto;
import com.i_sys.messanger.web.exceptions.NotFoundException;
import com.i_sys.messanger.web.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceBean {

    private final NotificationServiceDao notificationServiceDao;

    private void ValidatePageNumberAndSize(int pageNumber, int size) throws Exception {
        if (pageNumber < 1) {
            throw new ValidationException("pageNumber must be more or equal 1");
        }

        if (size < 1) {
            throw new ValidationException("size must be more or equal 1");
        }
    }

    public Page<NotificationDto> getAllNotifications(UUID userId, int pageNumber,
                                                     int size) throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "getAllNotifications(" + userId + "," + pageNumber + ","
                + size + ")");

        ValidatePageNumberAndSize(pageNumber, size);

        Page<NotificationDto> result = notificationServiceDao
                .getAllNotifications(userId, pageNumber, size);

        log.info("Method of NotificationServiceBean: " +
                "getAllNotifications(" + userId + "," + pageNumber + ","
                + size + ") successfully completed");

        return result;
    }

    public Page<NotificationDto> getAllReadNotifications(UUID userId, int pageNumber,
                                                         int size) throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "getAllReadNotifications(" + userId + "," + pageNumber + ","
                + size + ")");

        ValidatePageNumberAndSize(pageNumber, size);

        Page<NotificationDto> result = notificationServiceDao
                .getAllReadNotifications(userId, pageNumber, size);

        log.info("Method of NotificationServiceBean: " +
                "getAllReadNotifications(" + userId + "," + pageNumber + ","
                + size + ") successfully completed");

        return result;
    }

    public Page<NotificationDto> getAllUnReadNotifications(UUID userId, int pageNumber,
                                                           int size) throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "getAllUnReadNotifications(" + userId + "," + pageNumber + ","
                + size + ")");

        ValidatePageNumberAndSize(pageNumber, size);

        Page<NotificationDto> result = notificationServiceDao
                .getAllUnReadNotifications(userId, pageNumber, size);

        log.info("Method of NotificationServiceBean: " +
                "getAllUnReadNotifications(" + userId + "," + pageNumber + ","
                + size + ") successfully completed");

        return result;
    }

    public NotificationDto getNotificationById(UUID notificationId, UUID userId)
            throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "getNotificationById(" + notificationId + "," + userId + ")");

        NotificationDto result = notificationServiceDao
                .getNotificationById(notificationId, userId);

        if (result == null) {
            throw new NotFoundException("Notification with this " +
                    "id not found in database!");
        }

        log.info("Method of NotificationServiceBean: " +
                "getNotificationById(" + notificationId + "," + userId + ") " +
                "successfully completed");

        return result;
    }

    public NotificationDto createNotification(NotificationDto notification)
            throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "createNotification(" + notification + ")");

        if (notification.getSender() == null) {
            throw new ValidationException("Sender is null!");
        }

        if (notification.getText() == null) {
            throw new ValidationException("Text is null!");
        }

        NotificationDto result = notificationServiceDao
                .createNotification(notification);

        log.info("Method of NotificationServiceBean: " +
                "createNotification(" + notification + ") successfully completed");

        return result;
    }

    public NotificationDto updateNotification(UUID id, NotificationDto notification)
            throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "updateNotification(" + id + "," + notification + ")");

        if (notification.getSender() == null) {
            throw new ValidationException("Sender is null!");
        }

        if (notification.getText() == null) {
            throw new ValidationException("Text is null!");
        }

        NotificationDto result = notificationServiceDao
                .updateNotification(id, notification);


        log.info("Method of NotificationServiceBean: " +
                "updateNotification(" + id + "," + notification + ") " +
                "successfully completed");

        return result;
    }

    public void deleteNotification(UUID id) {
        log.info("Call method of NotificationServiceBean: " +
                "deleteNotification(" + id + ")");

        notificationServiceDao.deleteNotification(id);

        log.info("Method of NotificationServiceBean: " +
                "deleteNotification(" + id + ") successfully completed");
    }

    public void doRead(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceBean: " +
                "doRead(" + notificationId + ","
                + userId + ")");

        notificationServiceDao.doRead(notificationId, userId);

        log.info("Method of NotificationServiceBean: doRead(" + notificationId + ","
                + userId + ") successfully completed");
    }

    public void doUnRead(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceBean: doUnRead(" + notificationId + ","
                + userId + ")");

        notificationServiceDao.doUnRead(notificationId, userId);

        log.info("Method of NotificationServiceBean: doUnRead(" + notificationId + ","
                + userId + ") successfully completed");
    }
}
