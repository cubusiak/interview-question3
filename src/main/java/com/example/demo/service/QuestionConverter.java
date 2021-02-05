package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionThreadDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.ThreadReplyDTO;
import com.example.demo.model.Question;
import com.example.demo.model.Reply;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class QuestionConverter {

    public QuestionDTO convertToReplyDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .author(question.getAuthor())
                .message(question.getMessage())
                .replies(question.getReplies() == null ? 0 : question.getReplies().size())
                .build();
    }

    public Question convertToQuestion(QuestionDTO questionDTO) {
        return Question.builder()
                .author(questionDTO.getAuthor())
                .message(questionDTO.getMessage())
                .build();
    }

    public Reply convertToReply(ReplyDTO replyDTO) {
        return Reply.builder()
                .author(replyDTO.getAuthor())
                .message(replyDTO.getMessage())
                .build();
    }

    public ReplyDTO convertToReplyDTO(Reply reply, Long questionId) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .questionId(questionId)
                .author(reply.getAuthor())
                .message(reply.getMessage())
                .build();
    }

    public QuestionThreadDTO convertToQuestionThreadDTO(Question question) {
        return QuestionThreadDTO.builder()
                .id(question.getId())
                .author(question.getAuthor())
                .message(question.getMessage())
                .replies(question.getReplies()
                        .stream()
                        .map(this::convertToThreadReplyDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ThreadReplyDTO convertToThreadReplyDTO(Reply reply) {
        return ThreadReplyDTO.builder()
                .id(reply.getId())
                .author(reply.getAuthor())
                .message(reply.getMessage())
                .build();
    }

}
