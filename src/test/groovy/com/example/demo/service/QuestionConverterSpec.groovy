package com.example.demo.service

import com.example.demo.dto.QuestionDTO
import com.example.demo.dto.ReplyDTO
import com.example.demo.model.Question
import com.example.demo.model.Reply
import spock.lang.Specification

class QuestionConverterSpec extends Specification {

    def converter = new QuestionConverter()

    def "should convert question to questionDTO"() {
        given: "prepare model"
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
        def questionDTO = converter.convertToReplyDTO(question)

        then: "check result with model"
        verifyAll(questionDTO) {
            id == question.id
            author == question.author
            message == question.message
            replies == question.replies.size()
        }
    }

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

    def "should convert reply to replyDTO"() {
        given: "prepare model"
        def reply = Reply.builder()
                .id(1L)
                .author("Jack")
                .message("Text")
                .build()

        when: "use question converter"
        def replyDTO = converter.convertToReplyDTO(reply, 2L)

        then: "check result with model"
        verifyAll(replyDTO) {
            id == reply.id
            questionId == 2L
            author == reply.author
            message == reply.message
        }
    }

    def "should convert question to questionThreadDTO"() {
        given: "prepare model"
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

        then: "check result with model"
        verifyAll(questionThreadDTO) {
            id == question.id
            author == question.author
            message == question.message
            replies.size() == question.replies.size()
            replies.get(0).id == 2L
        }
    }

    def "should convert reply to ReplyThreadDTO"() {
        given: "prepare model"
        def reply = Reply.builder()
                .id(1L)
                .author("Jack")
                .message("Text")
                .build()

        when: "use question converter"
        def replyThreadDTO = converter.convertToThreadReplyDTO(reply)

        then: "check result with model"
        verifyAll(replyThreadDTO) {
            id == reply.id
            author == reply.author
            message == reply.message
        }
    }

}
