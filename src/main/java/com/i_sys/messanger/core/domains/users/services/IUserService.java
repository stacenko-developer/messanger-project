package com.i_sys.messanger.core.domains.users.services;

import com.i_sys.messanger.data.users.User;
import com.i_sys.messanger.web.controllers.users.dto.UserDtoPostOrPut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IUserService {
    List<User> getAllUsers() throws Exception;

    User getUserById(UUID id) throws Exception;

    User createUser(UserDtoPostOrPut user) throws Exception;

    User updateUser(UUID id, UserDtoPostOrPut user) throws Exception;

    void deleteUser(UUID id) throws Exception;
}
