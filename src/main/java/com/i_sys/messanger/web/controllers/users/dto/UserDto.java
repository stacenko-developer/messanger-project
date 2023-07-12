package com.i_sys.messanger.web.controllers.users.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;
    private UUID externalId;
}
