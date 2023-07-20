package com.i_sys.messanger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllNotificationsDto {
    private Boolean isRead;
    private int pageNumber;
    private int size;
}
