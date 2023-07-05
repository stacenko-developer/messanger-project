package com.i_sys.messanger.core.domains.users.services;

import com.i_sys.messanger.core.domains.users.repositories.IUserRepository;
import com.i_sys.messanger.data.users.User;
import com.i_sys.messanger.web.controllers.users.dto.UserDtoPostOrPut;
import com.i_sys.messanger.web.exceptions.NotFoundException;
import com.i_sys.messanger.web.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class.getName());
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() throws Exception {

        log.info("Call Method of UserService: getAllUsers()");

        List<User> result = userRepository.findAll();

        log.info("Method of UserService: getAllUsers() successfully completed");

        return result;
    }

    public User getUserById(UUID id) throws Exception {
        log.info("Call Method of UserService: getUserById(" + id + ")");

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new NotFoundException("The user with the specified id was not found in the system!");
        }

        log.info("Method of UserService: getUserById(" + id + ") successfully completed");

        return user;
    }

    public User createUser(UserDtoPostOrPut user) throws Exception {
        log.info("Call Method of UserService: createUser(" + user + ")");

        if (user.login == null || user.password == null) {
            throw new ValidationException("User's data are null!");
        }

        if (userRepository.findByLogin(user.login).orElse(null) != null) {
            throw new ValidationException("User with this login has already registered!");
        }

        User entity = userRepository.save(new User(user.login, user.password));

        log.info("Call Method of UserService: createUser(" + user + ") successfully completed");

        return entity;
    }

    public User updateUser(UUID id, UserDtoPostOrPut user) throws Exception {
        log.info("Call Method of UserService: updateUser(" + id + "," + user + ")");

        User entity = userRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new NotFoundException("The user with the specified id was not found in the system!");
        }

        if (user.login == null || user.password == null) {
            throw new ValidationException("User's data are null!");
        }

        if (userRepository.findByLogin(user.login) != null) {
            throw new ValidationException("User with this login has already registered!");
        }

        entity.setLogin(user.login);
        entity.setPassword(user.password);

        User result = userRepository.save(entity);

        log.info("Call Method of UserService: updateUser(" + id + "," + user + ") successfully completed");

        return result;
    }

    public void deleteUser(UUID id) throws Exception {
        log.info("Call Method of UserService: deleteUser(" + id + ")");

        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            throw new NotFoundException("The user with the specified id was not found in the system!");
        }

        userRepository.delete(result);
        log.info("Method of UserService: deleteUser(" + id + ") successfully completed");
    }
}
