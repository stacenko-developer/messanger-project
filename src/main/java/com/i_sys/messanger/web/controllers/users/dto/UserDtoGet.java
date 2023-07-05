package com.i_sys.messanger.web.controllers.users.dto;

import com.i_sys.messanger.data.users.User;

import java.util.UUID;

public class UserDtoGet {
    public UUID id;
    public String login;
    public String password;

    public UserDtoGet(User user) {
        id = user.getId();
        login = user.getLogin();
        password = user.getPassword();
    }
}
