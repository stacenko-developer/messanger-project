package com.i_sys.messanger.core.domains.users.repositories;

import com.i_sys.messanger.data.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
}
