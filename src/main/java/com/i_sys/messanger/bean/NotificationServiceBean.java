package com.i_sys.messanger.bean;

import com.i_sys.messanger.dao.notification.service.NotificationServiceDao;
import com.i_sys.messanger.dto.NotificationDto;
import com.i_sys.messanger.exception.NotFoundException;
import com.i_sys.messanger.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceBean {

    private final NotificationServiceDao notificationServiceDao;

    private void validateSenderAndText(String sender, String text) throws Exception {
        if (sender == null || sender.trim().isEmpty()) {
            throw new ValidationException("Sender is null!");
        }

        if (text == null || text.trim().isEmpty()) {
            throw new ValidationException("Text is null!");
        }
    }

    public Page<NotificationDto> getAllNotifications(UUID userId, boolean isReadable,
                                                     boolean isUnReadable, int pageNumber,
                                                     int size) throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "getAllNotifications(" + userId + "," + pageNumber + ","
                + size + ")");

        if (pageNumber < 1) {
            throw new ValidationException("pageNumber must be more or equal 1");
        }

        if (size < 1) {
            throw new ValidationException("size must be more or equal 1");
        }

        Page<NotificationDto> result = new PageImpl<>(new ArrayList<>());

        if (isReadable && isUnReadable) {
            result = notificationServiceDao.getAllNotifications(userId, pageNumber, size);
        }
        else if (isReadable) {
            result = notificationServiceDao.getAllReadNotifications(userId, pageNumber, size);
        }
        else if (isUnReadable) {
            result = notificationServiceDao.getAllUnReadNotifications(userId, pageNumber, size);
        }

        log.info("Method of NotificationServiceBean: " +
                "getAllNotifications(" + userId + "," + pageNumber + ","
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

        validateSenderAndText(notification.getSender(), notification.getText());

        NotificationDto result = notificationServiceDao
                .createNotification(notification);

        log.info("Method of NotificationServiceBean: " +
                "createNotification(" + notification + ") successfully completed");

        return result;
    }

    public NotificationDto updateNotification(UUID id, UUID userId, NotificationDto notification)
            throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "updateNotification(" + id + "," + notification + ")");

        if (notificationServiceDao.getNotificationById(id, userId) == null) {
            throw new NotFoundException("Notification with this " +
                    "id not found in database!");
        }

        validateSenderAndText(notification.getSender(), notification.getText());

        if (notification.isRead()) {
            notificationServiceDao.doRead(id, userId);
        }

        if (!notification.isRead()) {
            notificationServiceDao.doUnRead(id, userId);
        }

        NotificationDto result = notificationServiceDao
                .updateNotification(id, notification);

        result.setRead(notification.isRead());

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
}
