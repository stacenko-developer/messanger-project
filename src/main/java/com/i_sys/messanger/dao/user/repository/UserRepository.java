package com.i_sys.messanger.dao.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.i_sys.messanger.dao.user.entity.User;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByExternalId(UUID externalId);
}
