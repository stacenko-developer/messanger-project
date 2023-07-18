package com.i_sys.messanger.bean;

import com.i_sys.messanger.dao.notification.service.NotificationServiceDao;
import com.i_sys.messanger.dto.GetAllNotificationsDto;
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

    public Page<NotificationDto> getAllNotifications(UUID userId, GetAllNotificationsDto getAllNotificationsDto) throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "getAllNotifications(" + getAllNotificationsDto + ")");

        Page<NotificationDto> result;

        int pageNumber = 1;
        int size = 10;

        if (getAllNotificationsDto == null) {
            result = notificationServiceDao.getAllNotifications(userId, pageNumber, size);
        }

        if (getAllNotificationsDto.getPageNumber() != null) {
            pageNumber = getAllNotificationsDto.getPageNumber();
        }

        if (getAllNotificationsDto.getSize() != null) {
            size = getAllNotificationsDto.getSize();
        }

        if (pageNumber < 1) {
            throw new ValidationException("pageNumber must be more than 1");
        }

        if (size < 1) {
            throw new ValidationException("size must be more than 1");
        }

        result = getAllNotificationsDto.getIsRead() == null
                ? notificationServiceDao.getAllNotifications(userId, pageNumber, size)
                : getAllNotificationsDto.getIsRead()
                    ? notificationServiceDao.getAllReadNotifications(userId, pageNumber, size)
                    : notificationServiceDao.getAllUnReadNotifications(userId, pageNumber, size);

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
