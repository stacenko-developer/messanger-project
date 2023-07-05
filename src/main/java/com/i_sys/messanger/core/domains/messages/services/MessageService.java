package com.i_sys.messanger.core.domains.messages.services;

import com.i_sys.messanger.core.domains.messages.repositories.IIsReadRepository;
import com.i_sys.messanger.core.domains.messages.repositories.IMessageRepository;
import com.i_sys.messanger.core.domains.users.repositories.IUserRepository;
import com.i_sys.messanger.data.messages.IsRead;
import com.i_sys.messanger.data.messages.Message;
import com.i_sys.messanger.data.users.User;
import com.i_sys.messanger.web.controllers.messages.dto.MessageDtoGet;
import com.i_sys.messanger.web.controllers.messages.dto.MessageDtoPostOrPut;
import com.i_sys.messanger.web.exceptions.NotFoundException;
import com.i_sys.messanger.web.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService implements IMessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class.getName());
    private final IMessageRepository messageRepository;
    private final IUserRepository userRepository;

    private final IIsReadRepository isReadRepository;

    @Autowired
    public MessageService(IMessageRepository messageRepository, IUserRepository userRepository, IIsReadRepository isReadRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.isReadRepository = isReadRepository;
    }

    public List<MessageDtoGet> getAllMessages(UUID userId) throws Exception {
        log.info("Call Method of MessageService: getAllMessages(" + userId + ")");

        if (userRepository.findById(userId).orElse(null) == null) {
            throw new NotFoundException("The user with the specified id was not found in the system!");
        }

        List<MessageDtoGet> result = new ArrayList<>();

        for (Message message : messageRepository.findAll()) {

            IsRead readMessage = isReadRepository.findByUserIdAndMessageId(userId, message.getId())
                            .orElse(null);

            if (readMessage == null) {
                throw new Exception("Information about reading message is not found in database!");
            }

            result.add(new MessageDtoGet(message.getId(), message.getText(),
                    message.getSender().getId(), readMessage.isRead()));
        }

//        String currentUser = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();

        log.info("Method of MessageService: getAllMessages(" + userId + ") successfully completed");

        return result;
    }

    public MessageDtoGet getMessageById(UUID messageId, UUID userId) throws Exception {
        log.info("Call Method of MessageService: getMessageById(" + messageId + "," + userId + ")");
        Message message = messageRepository.findById(messageId).orElse(null);

        if (message == null) {
            throw new NotFoundException("The message with the specified id was not found in the system!");
        }

        if (userRepository.findById(userId).orElse(null) == null) {
            throw new NotFoundException("The user with the specified id was not found in the system!");
        }

        IsRead readMessage = isReadRepository.findByUserIdAndMessageId(userId, messageId).orElse(null);

        if (readMessage == null) {
            throw new Exception("Information about reading message is not found in database!");
        }

        log.info("Call Method of MessageService: getMessageById(" + messageId + "," + userId + ") successfully completed");

        return new MessageDtoGet(message.getId(), message.getText(), message.getSender().getId(),
                readMessage.isRead());
    }

    public Message createMessage(MessageDtoPostOrPut message) throws Exception {
        log.info("Call Method of MessageService: createMessage(" + message + ")");

        if (message.text == null) {
            throw new ValidationException("The text message is null!");
        }

        User sender = userRepository.findById(message.senderId).orElse(null);

        if (sender == null) {
            throw new NotFoundException("The sender with the specified id was not found in the system!");
        }

        Message result = messageRepository.save(new Message(message.text, sender));

        for (User user: userRepository.findAll()) {
            isReadRepository.save(new IsRead(user, result, false));
        }

        log.info("Call Method of MessageService: createMessage(" + message + ") successfully completed");

        return result;
    }

    public Message updateMessage(UUID id, MessageDtoPostOrPut message) throws Exception {
        log.info("Call Method of MessageService: updateMessage(" + id + "," + message + ")");

        Message entity = messageRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new NotFoundException("The message with the specified id was not found in the system!");
        }

        User sender = userRepository.findById(message.senderId).orElse(null);

        if (sender == null) {
            throw new NotFoundException("The sender with the specified id was not found in the system!");
        }

        entity.setText(message.text);
        entity.setSender(sender);

        Message result = messageRepository.save(entity);

        log.info("Call Method of MessageService: updateMessage(" + id + "," + message + ") successfully completed");

        return result;
    }

    public void deleteMessage(UUID id) throws Exception {
        log.info("Call Method of MessageService: deleteMessage(" + id + ")");
        Message result = messageRepository.findById(id).orElse(null);

        if (result == null) {
            throw new NotFoundException("The message with the specified id was not found in the system!");
        }

        messageRepository.delete(result);
        log.info("Method of MessageService: deleteMessage(" + id + ") successfully completed");
    }

    public void doRead(UUID messageId, UUID userId) throws Exception {
        log.info("Call method of MessageService: doRead(" + messageId + "," + userId + ")");
        changeReadFlag(messageId, userId, true);
        log.info("Method of MessageService: doRead(" + messageId + "," + userId + ") successfully completed");
    }

    public void doUnRead(UUID messageId, UUID userId) throws Exception {
        log.info("Call method of MessageService: doUnRead(" + messageId + "," + userId + ")");
        changeReadFlag(messageId, userId, false);
        log.info("Method of MessageService: doUnRead(" + messageId + "," + userId + ") successfully completed");
    }

    private void changeReadFlag(UUID messageId, UUID userId, boolean isRead) throws Exception {
        if (messageRepository.findById(messageId).orElse(null) == null) {
            throw new NotFoundException("The message with the specified id was not found in the system!");
        }

        if (userRepository.findById(userId).orElse(null) == null) {
            throw new NotFoundException("The user with the specified id was not found in the system!");
        }

        IsRead readMessage = isReadRepository.findByUserIdAndMessageId(userId, messageId)
                .orElse(null);

        if (readMessage == null) {
            throw new Exception("Information about reading message is not found in database!");
        }

        readMessage.setRead(isRead);

        isReadRepository.save(readMessage);
    }
}
