package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ThreadReply DTO, handles data about a single reply to the message record.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThreadReplyDTO {

    private Long id;
    private String author;
    private String message;

}
