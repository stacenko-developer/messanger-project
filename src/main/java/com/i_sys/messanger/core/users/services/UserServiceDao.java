package com.i_sys.messanger.core.users.services;

import com.i_sys.messanger.core.users.repositories.UserRepository;
import com.i_sys.messanger.data.users.User;
import com.i_sys.messanger.data.users.UserConvertor;
import com.i_sys.messanger.web.controllers.users.dto.UserDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceDao {

    private final UserRepository userRepository;
    private final UserConvertor userConvertor;

    @Transactional
    public List<UserDto> getAllUsers() {
        log.info("Call method of UserServiceDao: getAllUsers()");

        List<UserDto> result = userRepository.findAll()
                .stream()
                .map(userConvertor::convertToDto)
                .collect(Collectors.toList());

        log.info("Method of UserServiceDao: " +
                "getAllUsers() successfully completed");

        return result;
    }

    @Transactional
    public UserDto getUserById(UUID id) {
        log.info("Call method of UserServiceDao: getUserById(" + id + ")");

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
           return null;
        }

        UserDto result = userConvertor.convertToDto(user);

        log.info("Method of UserServiceDao: " +
                "getUserById(" + id + ") successfully completed");

        return result;
    }

    @Transactional
    public UserDto createUser(UserDto user) {
        log.info("Call method of UserServiceDao: createUser(" + user + ")");

        UserDto result = userConvertor
                .convertToDto(userRepository
                        .save(userConvertor.convertToModel(user)));

        log.info("Method of UserService: " +
                "createUser(" + user + ") successfully completed");

        return result;
    }

    @Transactional
    public UserDto updateUser(UUID id, UserDto user) {
        log.info("Call method of UserServiceDao: " +
                "updateUser(" + id + "," + user + ")");

        User userForUpdate = userConvertor.convertToModel(user);

        userForUpdate.setId(id);

        UserDto result = userConvertor
                .convertToDto(userRepository
                        .save(userForUpdate));

        log.info("Method of UserServiceDao: " +
                "updateUser(" + id + "," + user + ") successfully completed");

        return result;
    }

    @Transactional
    public void deleteUser(UUID id) {
        log.info("Call method of UserServiceDao: deleteUser(" + id + ")");

        userRepository.delete(Objects
                .requireNonNull(userRepository
                        .findById(id).orElse(null)));

        log.info("Method of UserServiceDao: " +
                "deleteUser(" + id + ") successfully completed");
    }
}