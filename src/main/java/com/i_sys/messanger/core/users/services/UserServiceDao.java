package com.i_sys.messanger.core.users.services;

import com.i_sys.messanger.core.users.repositories.UserRepository;
import com.i_sys.messanger.data.users.User;
import com.i_sys.messanger.web.controllers.users.dto.UserDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceDao {
    private static final Logger log = LoggerFactory
            .getLogger(UserServiceDao.class.getName());
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        log.info("Call method of UserServiceDao: getAllUsers()");

        List<UserDto> result = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setExternalId(user.getExternalId());

            result.add(userDto);
        }

        log.info("Method of UserServiceDao: " +
                "getAllUsers() successfully completed");

        return result;
    }

    public UserDto getUserById(UUID id) {
        log.info("Call method of UserServiceDao: getUserById(" + id + ")");

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
           return null;
        }

        UserDto result = new UserDto();

        result.setId(user.getId());
        result.setId(user.getExternalId());

        log.info("Method of UserServiceDao: " +
                "getUserById(" + id + ") successfully completed");

        return result;
    }

    public UserDto createUser(UserDto user) {
        log.info("Call method of UserServiceDao: createUser(" + user + ")");

        User userForCreate = new User();
        userForCreate.setExternalId(user.getExternalId());

        User entity = userRepository.save(userForCreate);

        UserDto result = new UserDto();

        result.setId(entity.getId());
        result.setExternalId(entity.getExternalId());

        log.info("Method of UserService: " +
                "createUser(" + user + ") successfully completed");

        return result;
    }

    public UserDto updateUser(UUID id, UserDto user) {
        log.info("Call method of UserServiceDao: " +
                "updateUser(" + id + "," + user + ")");

        User userForUpdate = userRepository.findById(id).orElse(null);

        userForUpdate.setExternalId(user.getExternalId());

        User entity = userRepository.save(userForUpdate);

        UserDto result = new UserDto();

        result.setId(entity.getId());
        result.setExternalId(entity.getExternalId());

        log.info("Method of UserServiceDao: " +
                "updateUser(" + id + "," + user + ") successfully completed");

        return result;
    }

    public void deleteUser(UUID id) {
        log.info("Call method of UserServiceDao: deleteUser(" + id + ")");

        userRepository.delete(userRepository.findById(id).orElse(null));

        log.info("Method of UserServiceDao: " +
                "deleteUser(" + id + ") successfully completed");
    }
}