package com.i_sys.messanger.core.domains.messages.repositories;

import com.i_sys.messanger.data.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMessageRepository extends JpaRepository<Message, UUID> {

}
