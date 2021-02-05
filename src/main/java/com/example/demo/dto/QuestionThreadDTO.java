package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionThreadDTO {

    private Long id;
    private String author;
    private String message;
    private List<ThreadReplyDTO> replies;

}
