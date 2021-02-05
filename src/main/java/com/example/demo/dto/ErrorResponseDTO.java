package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Error Response DTO, shows error information to the user.
 * Showed information are handled manually and are limited to the minimum value data.
 */
@Getter
@Builder
public class ErrorResponseDTO {

    private final String code;
    private final String message;

}
