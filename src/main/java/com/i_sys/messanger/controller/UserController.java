package com.i_sys.messanger.controller;

import com.i_sys.messanger.dao.user.service.UserServiceDao;
import com.i_sys.messanger.dto.ResponseDto;
import com.i_sys.messanger.dto.UserDto;
import com.i_sys.messanger.exception.NotFoundException;
import com.i_sys.messanger.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceDao userServiceDao;

    @GetMapping
    public ResponseDto getAllUsers(int pageNumber, int size) {
        log.info("Call method of UserController: getAllUsers(" + pageNumber + "," +
                size + ")");

        ResponseDto responseDto = new ResponseDto();

        int defaultPageNumber = 1;
        int defaultSize = 5;

        if (pageNumber <= 0) {
            pageNumber = defaultPageNumber;
        }

        if (size <= 0) {
            size = defaultSize;
        }

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setContent(userServiceDao.getAllUsers(pageNumber, size));

        log.info("Method of UserController: " +
                "getAllUsers(" + pageNumber + "," + size + ") successfully completed");

        return responseDto;
    }

    @GetMapping({"id"})
    public ResponseDto getUserById(@RequestParam UUID id) throws Exception {

        log.info("Call method of UserController: getUserById(" + id + ")");

        ResponseDto responseDto = new ResponseDto();

        if (userServiceDao.getUserById(id) == null) {
            throw new NotFoundException("User with this id not found in system");
        }

        responseDto.setContent(userServiceDao.getUserById(id));
        responseDto.setStatus(HttpStatus.OK.value());

        log.info("Method of UserController: " +
                "getUserById(" + id + ") successfully completed");

        return responseDto;
    }

    @PostMapping
    public ResponseDto createUser(@RequestBody UserDto user) throws Exception {
        log.info("Call method of UserController: createUser(" + user + ")");
        ResponseDto responseDto = new ResponseDto();

        if (userServiceDao.getUserByExternalId(user.getExternalId()) != null) {
            throw new ValidationException("User with this external id has already added!");
        }

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setContent(userServiceDao.createUser(user));

        log.info("Method of UserController: " +
                "createUser(" + user + ") successfully completed");

        return responseDto;
    }

    @PutMapping({"id"})
    public ResponseDto updateUser(@RequestParam UUID id, @RequestBody UserDto user) throws Exception {
        log.info("Call method of UserController: " +
                "updateUser(" + id + "," + user + ")");
        ResponseDto responseDto = new ResponseDto();

        if (userServiceDao.getUserById(id) == null) {
            throw new NotFoundException("User with this id not found in system");
        }

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setContent(userServiceDao.updateUser(id, user));

        log.info("Method of UserController: " +
                "updateUser(" + id + "," + user + ") successfully completed");

        return responseDto;
    }

    @DeleteMapping({"id"})
    public ResponseDto deleteUser(@RequestParam UUID id) {
        log.info("Call method of UserController: deleteUser(" + id + ")");

        userServiceDao.deleteUser(id);

        log.info("Method of UserController: " +
                "deleteUser(" + id + ") successfully completed");

        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());

        return responseDto;
    }
}
