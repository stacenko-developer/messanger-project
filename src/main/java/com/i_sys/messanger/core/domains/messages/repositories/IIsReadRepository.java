package com.i_sys.messanger.core.domains.messages.repositories;

import com.i_sys.messanger.data.messages.IsRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IIsReadRepository extends JpaRepository<IsRead, UUID> {
    Optional<IsRead> findByUserIdAndMessageId(UUID userId, UUID messageId);
}

