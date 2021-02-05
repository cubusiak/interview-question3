package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecordNotFoundException extends Exception {
    public RecordNotFoundException() {
        super("Record not found exception.");
    }
}
