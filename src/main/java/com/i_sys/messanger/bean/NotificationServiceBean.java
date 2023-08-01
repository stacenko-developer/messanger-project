package com.i_sys.messanger.bean;

import com.i_sys.messanger.dao.notification.service.NotificationServiceDao;
import com.i_sys.messanger.dto.GetAllNotificationsDto;
import com.i_sys.messanger.dto.NotificationDto;
import com.i_sys.messanger.exception.NotFoundException;
import com.i_sys.messanger.exception.ValidationException;
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

    private void validateSenderAndText(String sender, String text) throws Exception {
        if (sender == null || sender.trim().isEmpty()) {
            throw new ValidationException("Sender is null!");
        }

        if (text == null || text.trim().isEmpty()) {
            throw new ValidationException("Text is null!");
        }
    }

    public Page<NotificationDto> getAllNotifications(UUID userId, GetAllNotificationsDto getAllNotificationsDto) {
        log.info("Call method of NotificationServiceBean: " +
                "getAllNotifications(" + userId + "," + getAllNotificationsDto + ")");

        Page<NotificationDto> result;

        int defaultPageNumber = 1;
        int defaultSize = 5;

        if (getAllNotificationsDto == null) {
            result = notificationServiceDao.getAllNotifications(userId, defaultPageNumber, defaultSize);
        }

        if (getAllNotificationsDto.getPageNumber() <= 0) {
            getAllNotificationsDto.setPageNumber(defaultPageNumber);
        }

        if (getAllNotificationsDto.getSize() <= 0) {
            getAllNotificationsDto.setSize(defaultSize);
        }

        result = getAllNotificationsDto.getIsRead() == null
                ? notificationServiceDao.getAllNotifications(userId, getAllNotificationsDto.getPageNumber(), getAllNotificationsDto.getSize())
                : getAllNotificationsDto.getIsRead()
                    ? notificationServiceDao.getAllReadNotifications(userId, getAllNotificationsDto.getPageNumber(), getAllNotificationsDto.getSize())
                    : notificationServiceDao.getAllUnReadNotifications(userId, getAllNotificationsDto.getPageNumber(), getAllNotificationsDto.getSize());

        log.info("Method of NotificationServiceBean: " +
                "getAllNotifications(" + userId + "," + getAllNotificationsDto
                + ") successfully completed");

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

    public NotificationDto updateNotification(UUID notificationId, UUID userId, NotificationDto notification)
            throws Exception {
        log.info("Call method of NotificationServiceBean: " +
                "updateNotification(" + notificationId + "," + userId + "," + notification + ")");

        if (notificationServiceDao.getNotificationById(notificationId, userId) == null) {
            throw new NotFoundException("Notification with this " +
                    "notificationId not found in database!");
        }

        validateSenderAndText(notification.getSender(), notification.getText());

        if (notification.isRead()) {
            notificationServiceDao.doRead(notificationId, userId);
        }

        if (!notification.isRead()) {
            notificationServiceDao.doUnRead(notificationId, userId);
        }

        NotificationDto result = notificationServiceDao
                .updateNotification(notificationId, notification);

        result.setRead(notification.isRead());

        log.info("Method of NotificationServiceBean: " +
                "updateNotification(" + notificationId + "," + userId + "," + notification + ") " +
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
