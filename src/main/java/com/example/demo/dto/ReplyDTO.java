package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Reply DTO, handles data about reply record.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    private Long questionId;
    private Long id;
    @NotNull
    @NotEmpty
    private String author;
    @NotNull
    @NotEmpty
    private String message;

}
