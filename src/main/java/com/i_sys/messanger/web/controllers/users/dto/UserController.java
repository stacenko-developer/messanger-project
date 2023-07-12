package com.i_sys.messanger.web.controllers.users.dto;

import com.i_sys.messanger.core.users.services.UserServiceBean;
import com.i_sys.messanger.web.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceBean userServiceBean;

    @GetMapping
    public ResponseEntity getAllUsers(int pageNumber, int size) throws Exception {
        log.info("Call method of UserController: getAllUsers(" + pageNumber +
                size + ")");

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(userServiceBean
                .getAllUsers(pageNumber, size));

        log.info("Method of UserController: " +
                "getAllUsers(" + pageNumber + "," + size + ") successfully completed");

        return result;
    }

    @GetMapping({"id"})
    public ResponseEntity getUserById(@RequestParam UUID id) throws Exception {

        log.info("Call method of UserController: getUserById(" + id + ")");

        if (id == null) {
            throw new ValidationException("Id is null!");
        }

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK)
                .body(userServiceBean.getUserById(id));

        log.info("Method of UserController: " +
                "getUserById(" + id + ") successfully completed");

        return result;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto user) throws Exception {
        log.info("Call method of UserController: createUser(" + user + ")");

        if (user == null) {
            throw new ValidationException("User is null!");
        }

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(userServiceBean
                .createUser(user));

        log.info("Method of UserController: " +
                "createUser(" + user + ") successfully completed");

        return result;
    }

    @PutMapping({"id"})
    public ResponseEntity updateUser(@RequestParam UUID id, @RequestBody UserDto user) throws Exception {
        log.info("Call method of UserController: " +
                "updateUser(" + id + "," + user + ")");

        if (id == null) {
            throw new ValidationException("Id is null!");
        }

        if (user == null) {
            throw new ValidationException("User is null!");
        }

        userServiceBean.getUserById(id);

        ResponseEntity result = ResponseEntity.status(HttpStatus.OK).body(userServiceBean
                .updateUser(id, user));

        log.info("Method of UserController: " +
                "updateUser(" + id + "," + user + ") successfully completed");

        return result;
    }

    @DeleteMapping({"id"})
    public ResponseEntity deleteUser(@RequestParam UUID id) throws Exception {
        log.info("Call method of UserController: deleteUser(" + id + ")");

        if (id == null) {
            throw new ValidationException("Id is null!");
        }

        userServiceBean.deleteUser(id);

        log.info("Method of UserController: " +
                "deleteUser(" + id + ") successfully completed");

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
