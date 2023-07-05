package com.i_sys.messanger.core.domains.messages.services;

import com.i_sys.messanger.data.messages.Message;
import com.i_sys.messanger.web.controllers.messages.dto.MessageDtoGet;
import com.i_sys.messanger.web.controllers.messages.dto.MessageDtoPostOrPut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IMessageService {
    List<MessageDtoGet> getAllMessages(UUID userId) throws Exception;

    MessageDtoGet getMessageById(UUID messageId, UUID userId) throws Exception;

    Message createMessage(MessageDtoPostOrPut message) throws Exception;

    Message updateMessage(UUID id, MessageDtoPostOrPut message) throws Exception;

    void deleteMessage(UUID id) throws Exception;

    void doRead(UUID messageId, UUID userId) throws Exception;

    void doUnRead(UUID messageId, UUID userId) throws Exception;
}
