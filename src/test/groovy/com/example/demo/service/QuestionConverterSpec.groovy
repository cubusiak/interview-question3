package com.example.demo.service

import com.example.demo.dto.QuestionDTO
import com.example.demo.dto.ReplyDTO
import com.example.demo.entity.Question
import com.example.demo.entity.Reply
import spock.lang.Specification

/**
 * Spock Test class, performs unit tests of mapping entities to DTOs; and DTOs to entities.
 */
class QuestionConverterSpec extends Specification {

    def converter = new QuestionConverter()

    /**
     * Scenario: Given question entity, method converts it to DTO.
     */
    def "should convert question to questionDTO"() {
        given: "prepare entity"
        def question = Question.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .replies(
                        Collections.singletonList(
                                Reply.builder()
                                        .id(2L)
                                        .author("Jakub")
                                        .message("Text2")
                                        .build())
                )
                .build()

        when: "use question converter"
        def questionDTO = converter.convertToQuestionDTO(question)

        then: "check result with entity"
        verifyAll(questionDTO) {
            id == question.id
            author == question.author
            message == question.message
            replies == question.replies.size()
        }
    }

    /**
     * Scenario: Given question DTO, method converts it to entity.
     */
    def "should convert questionDTO to question"() {
        given: "prepare dto"
        def questionDTO = QuestionDTO.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .build()

        when: "use question converter"
        def question = converter.convertToQuestion(questionDTO)

        then: "check result with dto"
        verifyAll(question) {
            id == question.id
            author == question.author
            message == question.message
            replies == question.replies
        }
    }

    /**
     * Scenario: Given reply DTO, method converts it to entity.
     */
    def "should convert replyDTO to reply"() {
        given: "prepare dto"
        def replyDTO = ReplyDTO.builder()
                .author("Jack")
                .message("Text")
                .build()

        when: "use question converter"
        def reply = converter.convertToReply(replyDTO)

        then: "check result with dto"
        verifyAll(reply) {
            id == replyDTO.id
            question == null
            author == replyDTO.author
            message == replyDTO.message
        }
    }

    /**
     * Scenario: Given reply entity, method converts it to DTO.
     */
    def "should convert reply to replyDTO"() {
        given: "prepare entity"
        def reply = Reply.builder()
                .id(1L)
                .author("Jack")
                .message("Text")
                .build()

        when: "use question converter"
        def replyDTO = converter.convertToReplyDTO(reply, 2L)

        then: "check result with entity"
        verifyAll(replyDTO) {
            id == reply.id
            questionId == 2L
            author == reply.author
            message == reply.message
        }
    }

    /**
     * Scenario: Given question entity, method converts it to questionThread DTO.
     */
    def "should convert question to questionThreadDTO"() {
        given: "prepare entity"
        def replyQuestion = Question.builder()
                .id(1L)
                .build()

        def question = Question.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .replies(
                        Collections.singletonList(
                                Reply.builder()
                                        .id(2L)
                                        .question(replyQuestion)
                                        .author("Jakub")
                                        .message("Text2")
                                        .build())
                )
                .build()

        when: "use question converter"
        def questionThreadDTO = converter.convertToQuestionThreadDTO(question)

        then: "check result with entity"
        verifyAll(questionThreadDTO) {
            id == question.id
            author == question.author
            message == question.message
            replies.size() == question.replies.size()
            replies.get(0).id == 2L
        }
    }

    /**
     * Scenario: Given reply entity, method converts it to ReplyThread DTO.
     */
    def "should convert reply to ReplyThreadDTO"() {
        given: "prepare entity"
        def reply = Reply.builder()
                .id(1L)
                .author("Jack")
                .message("Text")
                .build()

        when: "use question converter"
        def replyThreadDTO = converter.convertToThreadReplyDTO(reply)

        then: "check result with entity"
        verifyAll(replyThreadDTO) {
            id == reply.id
            author == reply.author
            message == reply.message
        }
    }

}
