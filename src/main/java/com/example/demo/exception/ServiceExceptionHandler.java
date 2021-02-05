package com.example.demo.exception;

import com.example.demo.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(1)
@Slf4j
class ServiceExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ErrorResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return ErrorResponseDTO.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message("Please check your request.")
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RecordNotFoundException.class})
    @ResponseBody
    public ErrorResponseDTO handleRecordNotFoundException(RecordNotFoundException ex) {
        log.error(ex.getMessage());
        return ErrorResponseDTO.builder()
                .code(HttpStatus.NOT_FOUND.toString())
                .message("No record exist.")
                .build();
    }

}
