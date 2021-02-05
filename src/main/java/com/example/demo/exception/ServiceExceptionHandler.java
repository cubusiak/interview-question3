package com.example.demo.exception;

import com.example.demo.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception Handler, logs the error message to the console;
 * provides custom error code and message for MethodArgumentNotValidException, RecordNotFoundException and Exception.
 */
@ControllerAdvice
@Slf4j
class ServiceExceptionHandler {

    /**
     * Custom Method Argument Not Valid Exception, logs the error message to the console and provides custom error response.
     *
     * @param ex MethodArgumentNotValidException
     * @return ErrorResponseDTO
     */
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

    /**
     * Custom Record Not Found Exception, logs the error message to the console and provides custom error response.
     *
     * @return ErrorResponseDTO
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RecordNotFoundException.class})
    @ResponseBody
    public ErrorResponseDTO handleRecordNotFoundException() {
        String message = "No record exist.";
        log.error(message);
        return ErrorResponseDTO.builder()
                .code(HttpStatus.NOT_FOUND.toString())
                .message(message)
                .build();
    }

    /**
     * Custom Exception, logs the error message to the console and provides custom error response.
     *
     * @param ex Exception
     * @return ErrorResponseDTO
     */
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
