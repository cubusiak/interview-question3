package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDTO {

    private final String code;
    private final String message;

}
