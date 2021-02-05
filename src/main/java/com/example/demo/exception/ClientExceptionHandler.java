package com.example.demo.exception;

import com.example.demo.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(2147483646)
@Slf4j
class ClientExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ErrorResponseDTO handleException(Exception ex) {
        log.error(ex.getMessage());
        return ErrorResponseDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message("An internal error occurred.")
                .build();
    }

}
