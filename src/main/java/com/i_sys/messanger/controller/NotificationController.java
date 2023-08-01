package com.i_sys.messanger.controller;

import com.i_sys.messanger.bean.NotificationServiceBean;
import com.i_sys.messanger.dto.GetAllNotificationsDto;
import com.i_sys.messanger.dto.NotificationDto;
import com.i_sys.messanger.dto.ResponseDto;
import com.i_sys.messanger.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationServiceBean notificationServiceBean;

    @GetMapping
    public ResponseDto getAllNotifications(@RequestParam UUID userId, GetAllNotificationsDto getAllNotificationsDto) throws Exception {
        log.info("Call method of NotificationController: " +
                "getAllNotifications(" + userId + "," + getAllNotificationsDto + ")");

        ResponseDto responseDto = new ResponseDto();

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setContent(notificationServiceBean.getAllNotifications(userId, getAllNotificationsDto));

        log.info("Method of NotificationController: " +
                "getAllNotifications(" + userId + "," + getAllNotificationsDto + ") successfully completed");

        return responseDto;
    }

    @GetMapping({"id"})
    public ResponseDto getNotificationById(@RequestParam UUID notificationId,
                                              @RequestParam UUID userId) throws Exception {
        log.info("Call method of NotificationController: " +
                "getNotificationById(" + notificationId + "," + userId + ")");
        ResponseDto responseDto = new ResponseDto();

        responseDto.setContent(notificationServiceBean
                .getNotificationById(notificationId, userId));
        responseDto.setStatus(HttpStatus.OK.value());

        log.info("Method of NotificationController: " +
                "getNotificationById(" + notificationId + "," + userId + ") " +
                "successfully completed");

        return responseDto;
    }

    @PostMapping
    public ResponseDto createNotification(@RequestBody NotificationDto notification) throws Exception {
        log.info("Call method of NotificationController: " +
                "createNotification(" + notification + ")");

        if (notification == null) {
            throw new ValidationException("Notification is null!");
        }

        ResponseDto responseDto = new ResponseDto();

        responseDto.setContent(notificationServiceBean.createNotification(notification));
        responseDto.setStatus(HttpStatus.OK.value());

        log.info("Method of NotificationController: " +
                "createNotification(" + notification + ") successfully completed");

        return responseDto;
    }

    @PutMapping({"id"})
    public ResponseDto updateNotification(@RequestParam UUID notificationId, @RequestParam UUID userId,
                                          @RequestBody NotificationDto notification
                                             ) throws Exception {
        log.info("Call method of NotificationController: " +
                "updateNotification(" + notificationId + "," + userId + "," + notification + ")");

        if (notification == null) {
            throw new ValidationException("Notification is null!");
        }

        ResponseDto responseDto = new ResponseDto();

        responseDto.setContent(notificationServiceBean
                .updateNotification(notificationId, userId, notification));
        responseDto.setStatus(HttpStatus.OK.value());

        log.info("Method of NotificationController: " +
                "updateNotification(" + notificationId + "," + userId + ","
                + notification + ") " + "successfully completed");

        return responseDto;
    }

    @DeleteMapping({"id"})
    public ResponseDto deleteNotification(@RequestParam UUID id) {
        log.info("Call method of NotificationController: " +
                "deleteNotification(" + id + ")");

        notificationServiceBean.deleteNotification(id);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());

        log.info("Method of NotificationController: " +
                "deleteNotification(" + id + ") successfully completed");

        return responseDto;
    }
}
