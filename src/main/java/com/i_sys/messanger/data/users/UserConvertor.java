package com.i_sys.messanger.data.users;

import com.i_sys.messanger.web.controllers.users.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {
    private final ModelMapper modelMapper;

    public UserConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convertToModel(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
