package com.i_sys.messanger.web.controllers.messages.dto;

import com.i_sys.messanger.data.messages.Message;

import java.util.UUID;

public class MessageDtoGet {
    public UUID id;
    public String text;
    public UUID senderId;
    public boolean isRead;

    public MessageDtoGet(UUID id, String text, UUID senderId, boolean isRead) {
        this.id = id;
        this.text = text;
        this.senderId = senderId;
        this.isRead = isRead;
    }
}
