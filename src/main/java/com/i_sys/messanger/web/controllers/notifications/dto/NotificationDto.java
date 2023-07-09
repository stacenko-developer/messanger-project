package com.i_sys.messanger.web.controllers.notifications.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotificationDto {
    private UUID id;
    private String sender;
    private String text;
    private boolean isRead;
}
