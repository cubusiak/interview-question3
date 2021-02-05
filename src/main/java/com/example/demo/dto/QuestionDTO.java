package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Question DTO, handles data about question record.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    private Long id;
    @NotNull
    @NotEmpty
    private String author;
    @NotNull
    @NotEmpty
    private String message;
    private int replies;

}
