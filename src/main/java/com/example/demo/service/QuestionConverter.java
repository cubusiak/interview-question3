package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionThreadDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.ThreadReplyDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Question converter, maps entities to DTOs; and DTOs to entities.
 */
@Component
class QuestionConverter {

    /**
     * Given a question entity, parses it to a question DTO
     *
     * @param question is a question entity to be parsed
     * @return QuestionDTO
     */
    public QuestionDTO convertToQuestionDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .author(question.getAuthor())
                .message(question.getMessage())
                .replies(question.getReplies() == null ? 0 : question.getReplies().size())
                .build();
    }

    /**
     * Given a question DTO, parses it to a question entity
     *
     * @param questionDTO is a question DTO to be parsed
     * @return Question
     */
    public Question convertToQuestion(QuestionDTO questionDTO) {
        return Question.builder()
                .author(questionDTO.getAuthor())
                .message(questionDTO.getMessage())
                .build();
    }

    /**
     * Given a reply DTO, parses it to a reply entity
     *
     * @param replyDTO is a reply DTO to be parsed
     * @return Reply
     */
    public Reply convertToReply(ReplyDTO replyDTO) {
        return Reply.builder()
                .author(replyDTO.getAuthor())
                .message(replyDTO.getMessage())
                .build();
    }

    /**
     * Given a reply entity, parses it to a reply DTO
     *
     * @param reply      is a reply entity to be parsed
     * @param questionId is a number with questionId
     * @return Reply
     */
    public ReplyDTO convertToReplyDTO(Reply reply, Long questionId) {
        return ReplyDTO.builder()
                .id(reply.getId())
                .questionId(questionId)
                .author(reply.getAuthor())
                .message(reply.getMessage())
                .build();
    }

    /**
     * Given a question entity, parses it to a question thread DTO
     *
     * @param question is a question entity to be parsed
     * @return QuestionThreadDTO
     */
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

    /**
     * Given a reply entity, parses it to a thread reply DTO
     *
     * @param reply is a reply entity to be parsed
     * @return ThreadReplyDTO
     */
    public ThreadReplyDTO convertToThreadReplyDTO(Reply reply) {
        return ThreadReplyDTO.builder()
                .id(reply.getId())
                .author(reply.getAuthor())
                .message(reply.getMessage())
                .build();
    }

}
