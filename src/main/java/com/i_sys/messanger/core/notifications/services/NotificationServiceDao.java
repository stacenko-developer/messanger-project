package com.i_sys.messanger.core.notifications.services;

import com.i_sys.messanger.core.notifications.repositories.NotificationRepository;
import com.i_sys.messanger.core.notifications.repositories.NotificationViewRepository;
import com.i_sys.messanger.core.users.repositories.UserRepository;
import com.i_sys.messanger.data.notifications.Notification;
import com.i_sys.messanger.data.notifications.NotificationConvertor;
import com.i_sys.messanger.data.notifications.NotificationView;
import com.i_sys.messanger.web.controllers.notifications.dto.NotificationDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceDao {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationViewRepository notificationViewRepository;
    private final NotificationConvertor notificationConvertor;

    @Transactional
    public Page<NotificationDto> getAllNotifications(UUID userId, int pageNumber, int size) {
        log.info("Call method of NotificationServiceDao: getAllNotifications("
                + userId + ")");

        Page<NotificationDto> result = notificationRepository
                .findAll(PageRequest.of(pageNumber, size))
                .map(notification -> notificationConvertor.convertToDto(notification, userId));

        log.info("Method of NotificationServiceDao: " +
                "getAllNotifications(" + userId + ") successfully completed");

        return result;
    }

    @Transactional
    public List<NotificationDto> getAllReadNotifications(UUID userId, int pageNumber, int size) {
        log.info("Call method of NotificationServiceDao: getAllReadNotifications("
                + userId + ")");


        List<NotificationDto> result = new ArrayList<>();

        for (NotificationView nw : notificationViewRepository.findByUserId(userId)) {
            result.add(notificationConvertor.convertToDto(notificationRepository.findById(nw.getNotification().getId()).orElse(null), userId));
        }

        log.info("Method of NotificationServiceDao: " +
                "getAllReadNotifications(" + userId + ") successfully completed");


        return result;
    }

    @Transactional
    public Page<NotificationDto> getAllUnReadNotifications(UUID userId, int pageNumber, int size) {
        log.info("Call method of NotificationServiceDao: getAllUnReadNotifications("
                + userId + ")");

        Page<NotificationDto> result = notificationRepository.findAll(PageRequest.of(pageNumber, size))
                .map(notification -> notificationConvertor.convertToDto(notification, userId));


        log.info("Method of NotificationServiceDao: " +
                "getAllUnReadNotifications(" + userId + ") successfully completed");

        return result;
    }

    @Transactional
    public NotificationDto getNotificationById(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceDao: " +
                "getNotificationById(" + notificationId + "," + userId + ")");

        Notification notification = notificationRepository
                .findById(notificationId).orElse(null);

        if (notification == null) {
            return null;
        }

        NotificationDto result = notificationConvertor.convertToDto(notification, userId);

        log.info("Method of NotificationServiceDao: " +
                "getNotificationById(" + notificationId + "," + userId + ") " +
                "successfully completed");

        return result;
    }

    @Transactional
    public NotificationDto createNotification(NotificationDto notification) {
        log.info("Call method of NotificationServiceDao: " +
                "createNotification(" + notification + ")");

        NotificationDto result = notificationConvertor
                .convertToDto(notificationRepository
                .save(notificationConvertor.convertToModel(notification)));

        log.info("Method of NotificationServiceDao: " +
                "createNotification(" + notification + ") successfully completed");

        return result;
    }

    @Transactional
    public NotificationDto updateNotification(UUID id, NotificationDto notification) {
        log.info("Call method of NotificationServiceDao: " +
                "updateNotification(" + id + "," + notification + ")");

        Notification notificationForUpdate = notificationConvertor
                .convertToModel(notification);

        notificationForUpdate.setId(id);

        NotificationDto result = notificationConvertor
                .convertToDto(notificationRepository
                        .save(notificationForUpdate));

        log.info("Method of NotificationServiceDao: " +
                "updateNotification(" + id + "," + notification + ") " +
                "successfully completed");

        return result;
    }

    @Transactional
    public void deleteNotification(UUID id) {
        log.info("Call method of NotificationServiceDao: " +
                "deleteNotification(" + id + ")");

        notificationRepository.deleteById(id);

        log.info("Method of NotificationServiceDao: " +
                "deleteNotification(" + id + ") successfully completed");
    }

    @Transactional
    public void doRead(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceDao: doRead(" + notificationId + ","
                + userId + ")");

        NotificationView notificationView = notificationViewRepository
                .findByUserIdAndNotificationId(userId, notificationId).orElse(null);

        if (notificationView == null) {
            NotificationView view = new NotificationView();
            view.setNotification(notificationRepository.findById(notificationId).orElse(null));
            view.setUser(userRepository.findById(userId).orElse(null));

            notificationViewRepository.save(view);
        }

        log.info("Method of NotificationServiceDao: doRead(" + notificationId + ","
                + userId + ") successfully completed");
    }

    @Transactional
    public void doUnRead(UUID notificationId, UUID userId) {
        log.info("Call method of NotificationServiceDao: doUnRead(" + notificationId + ","
                + userId + ")");

        NotificationView notificationView = notificationViewRepository
                .findByUserIdAndNotificationId(userId, notificationId).orElse(null);

        if (notificationView != null) {
            notificationViewRepository.delete(notificationView);
        }

        log.info("Method of NotificationServiceDao: doUnRead(" + notificationId + ","
                + userId + ") successfully completed");
    }
}
