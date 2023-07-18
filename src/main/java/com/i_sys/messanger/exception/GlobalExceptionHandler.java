package com.i_sys.messanger.exception;

import com.i_sys.messanger.dto.ResponseDto;
import com.i_sys.messanger.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(NotFoundException.class)
    public ResponseDto handleException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setErrorCode(ErrorCode.ENTITY_NOT_FOUND);

        return responseDto;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseDto handleException(ValidationException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
        responseDto.setErrorCode(ErrorCode.VALIDATION_ERROR);

        return responseDto;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseDto handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDto.setErrorCode(ErrorCode.SERVER_ERROR);

        return responseDto;
    }
}
