package com.i_sys.messanger.dto;

import com.i_sys.messanger.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private int status;
    private Object content;
    private ErrorCode errorCode;
}
