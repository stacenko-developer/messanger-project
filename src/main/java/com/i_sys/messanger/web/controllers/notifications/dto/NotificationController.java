package com.i_sys.messanger.web.controllers.notifications.dto;

import com.i_sys.messanger.core.notifications.services.NotificationServiceBean;
import com.i_sys.messanger.core.users.services.UserServiceBean;
import com.i_sys.messanger.web.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationServiceBean notificationServiceBean;
    private final UserServiceBean userServiceBean;

    @GetMapping
    public ResponseEntity getAllNotifications(@RequestParam UUID userId,
                                              int pageNumber, int size) throws Exception {
        log.info("Call method of NotificationController: " +
                "getAllNotifications(" + userId + "," + pageNumber + ","
                + size + ")");

        if (userId == null) {
            throw new ValidationException("UserId is null!");
        }

        userServiceBean.getUserById(userId);

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(notificationServiceBean
                .getAllNotifications(userId, pageNumber, size));

        log.info("Method of NotificationController: " +
                "getAllNotifications(" + userId + "," + pageNumber + ","
                + size + ") successfully completed");

        return result;
    }

    @GetMapping("/getAllReadNotifications")
    public ResponseEntity getAllReadNotifications(@RequestParam UUID userId,
                                                  int pageNumber, int size) throws Exception {
        log.info("Call method of NotificationController: " +
                "getAllReadNotifications(" + userId + "," + pageNumber + ","
                + size + ")");

        if (userId == null) {
            throw new ValidationException("UserId is null!");
        }

        userServiceBean.getUserById(userId);

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(notificationServiceBean
                .getAllReadNotifications(userId, pageNumber, size));

        log.info("Method of NotificationController: " +
                "getAllReadNotifications(" + userId + "," + pageNumber + ","
                + size + ") successfully completed");

        return result;
    }

    @GetMapping("/getAllUnReadNotifications")
    public ResponseEntity getAllUnReadNotifications(@RequestParam UUID userId,
                                                    int pageNumber, int size) throws Exception {
        log.info("Call method of NotificationController: " +
                "getAllUnReadNotifications(" + userId + "," + pageNumber + ","
                + size + ")");

        if (userId == null) {
            throw new ValidationException("UserId is null!");
        }

        userServiceBean.getUserById(userId);

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(notificationServiceBean
                .getAllUnReadNotifications(userId, pageNumber, size));

        log.info("Method of NotificationController: " +
                "getAllUnReadNotifications(" + userId + "," + pageNumber + ","
                + size + ") successfully completed");

        return result;
    }

    @GetMapping({"id"})
    public ResponseEntity getNotificationById(@RequestParam UUID notificationId,
                                              @RequestParam UUID userId) throws Exception {
        log.info("Call method of NotificationController: " +
                "getNotificationById(" + notificationId + "," + userId + ")");

        if (notificationId == null) {
            throw new ValidationException("NotificationId is null!");
        }

        if (userId == null) {
            throw new ValidationException("UserId is null!");
        }

        userServiceBean.getUserById(userId);

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(notificationServiceBean
                .getNotificationById(notificationId, userId));

        log.info("Method of NotificationController: " +
                "getNotificationById(" + notificationId + "," + userId + ") " +
                "successfully completed");

        return result;
    }

    @PostMapping
    public ResponseEntity createNotification(@RequestBody NotificationDto notification) throws Exception {
        log.info("Call method of NotificationController: " +
                "createNotification(" + notification + ")");

        if (notification == null) {
            throw new ValidationException("Notification is null!");
        }

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(notificationServiceBean
                .createNotification(notification));

        log.info("Method of NotificationController: " +
                "createNotification(" + notification + ") successfully completed");

        return result;
    }

    @PutMapping({"id"})
    public ResponseEntity updateNotification(@RequestParam UUID id,
                                             @RequestBody NotificationDto notification) throws Exception {
        log.info("Call method of NotificationController: " +
                "updateNotification(" + id + "," + notification + ")");

        if (id == null) {
            throw new ValidationException("Id is null!");
        }

        if (notification == null) {
            throw new ValidationException("Notification is null!");
        }

        notificationServiceBean.getNotificationById(id, null);

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(notificationServiceBean
                .updateNotification(id, notification));

        log.info("Method of NotificationController: " +
                "updateNotification(" + id + "," + notification + ") " +
                "successfully completed");

        return result;
    }

    @DeleteMapping({"id"})
    public ResponseEntity deleteNotification(@RequestParam UUID id) throws Exception {
        log.info("Call method of NotificationController: " +
                "deleteNotification(" + id + ")");

        if (id == null) {
            throw new ValidationException("Id is null!");
        }

        notificationServiceBean.deleteNotification(id);

        log.info("Method of NotificationController: " +
                "deleteNotification(" + id + ") successfully completed");

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/doRead")
    public ResponseEntity doRead(@RequestParam UUID notificationId,
                                 @RequestParam UUID userId) throws Exception {
        log.info("Call method of NotificationController: " +
                "doRead(" + notificationId + ","
                + userId + ")");

        if (notificationId == null) {
            throw new ValidationException("NotificationId is null!");
        }

        if (userId == null) {
            throw new ValidationException("UserId is null!");
        }

        userServiceBean.getUserById(userId);

        notificationServiceBean.getNotificationById(notificationId, null);

        notificationServiceBean.doRead(notificationId, userId);

        log.info("Method of NotificationController: doRead(" + notificationId + ","
                + userId + ") successfully completed");

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/doUnRead")
    public ResponseEntity doUnRead(@RequestParam UUID notificationId, @RequestParam UUID userId) throws Exception {
        log.info("Call method of NotificationController: doUnRead(" + notificationId + ","
                + userId + ")");

        if (notificationId == null) {
            throw new ValidationException("NotificationId is null!");
        }

        if (userId == null) {
            throw new ValidationException("UserId is null!");
        }

        userServiceBean.getUserById(userId);

        notificationServiceBean.getNotificationById(notificationId, null);

        notificationServiceBean.doUnRead(notificationId, userId);

        log.info("Method of NotificationController: doUnRead(" + notificationId + ","
                + userId + ") successfully completed");

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
