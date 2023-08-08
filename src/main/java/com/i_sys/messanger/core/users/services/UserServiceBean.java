package com.i_sys.messanger.core.users.services;

import com.i_sys.messanger.web.controllers.users.dto.UserDto;
import com.i_sys.messanger.web.exceptions.NotFoundException;
import com.i_sys.messanger.web.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceBean {

    private final UserServiceDao userServiceDao;

    public Page<UserDto> getAllUsers(int pageNumber, int size) throws Exception {
        log.info("Call method of UserServiceBean: getAllUsers(" + pageNumber +
                size + ")");

        if (pageNumber < 1) {
            throw new ValidationException("pageNumber must be more or equal 1");
        }

        if (size < 1) {
            throw new ValidationException("size must be more or equal 1");
        }

        Page<UserDto> result = userServiceDao.getAllUsers(pageNumber, size);

        log.info("Method of UserServiceBean: " +
                "getAllUsers(" + pageNumber + "," + size + ") successfully completed");

        return result;
    }

    public UserDto getUserById(UUID id) throws Exception {
        log.info("Call method of UserServiceBean: getUserById(" + id + ")");

        UserDto result = userServiceDao.getUserById(id);

        if (result == null) {
            throw new NotFoundException("User with this id not " +
                    "found in database!");
        }

        log.info("Method of UserServiceBean: " +
                "getUserById(" + id + ") successfully completed");

        return result;
    }

    public UserDto createUser(UserDto user) throws ValidationException {
        log.info("Call method of UserServiceBean: createUser(" + user + ")");

        if (user.getExternalId() == null) {
            throw new ValidationException("User's externalId is null!");
        }

        UserDto result = userServiceDao.createUser(user);

        log.info("Method of UserServiceBean: " +
                "createUser(" + user + ") successfully completed");

        return result;
    }

    public UserDto updateUser(UUID id, UserDto user) throws Exception {
        log.info("Call method of UserServiceBean: " +
                "updateUser(" + id + "," + user + ")");

        if (user.getExternalId() == null) {
            throw new ValidationException("User's externalId is null!");
        }

        UserDto result = userServiceDao.updateUser(id, user);

        log.info("Method of UserServiceBean: " +
                "updateUser(" + id + "," + user + ") successfully completed");

        return result;
    }

    public void deleteUser(UUID id) {
        log.info("Call method of UserServiceBean: deleteUser(" + id + ")");

        userServiceDao.deleteUser(id);

        log.info("Method of UserServiceBean: " +
                "deleteUser(" + id + ") successfully completed");
    }
}