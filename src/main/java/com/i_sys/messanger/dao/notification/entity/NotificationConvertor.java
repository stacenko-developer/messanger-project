package com.i_sys.messanger.dao.notification.entity;

import com.i_sys.messanger.dto.NotificationDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class NotificationConvertor {
    private final ModelMapper modelMapper;

    public NotificationConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public NotificationDto convertToDto(Notification notification) {
        return modelMapper.map(notification, NotificationDto.class);
    }

    public NotificationDto convertToDto(Notification notification, UUID userId) {
        NotificationDto notificationDto = convertToDto(notification);

        var notificationView = notification.getNotificationViews()
                .stream()
                .filter(view -> view.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);

        notificationDto.setRead(notificationView != null);

        return notificationDto;
    }

    public Notification convertToModel(NotificationDto notificationDto) {
        return modelMapper.map(notificationDto, Notification.class);
    }
}
