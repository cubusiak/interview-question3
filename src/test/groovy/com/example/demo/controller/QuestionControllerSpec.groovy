package com.example.demo.controller

import com.example.demo.dto.QuestionDTO
import com.example.demo.dto.QuestionThreadDTO
import com.example.demo.dto.ReplyDTO
import com.example.demo.entity.Question
import com.example.demo.entity.Reply
import com.example.demo.repository.QuestionRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.databind.type.TypeFactory
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import java.nio.charset.StandardCharsets

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

/**
 * Spock Test class, performs integration tests of the available rest calls.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class QuestionControllerSpec extends Specification {

    MockMvc mockMvc
    @SpringBean
    QuestionRepository questionRepository = Mock()

    @Autowired
    WebApplicationContext webApplicationContext

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    /**
     * Scenario: Given a question record, method gets a question entity list and returns a question DTO list.
     */
    def "should return 200 when getting questions"() {
        given: "prepare response list"
        Question question = Question.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .build()
        questionRepository.findAll() >> Collections.singletonList(question)

        and:
        ObjectMapper objectMapper = new ObjectMapper()
        CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, QuestionDTO.class)

        when: "call get endpoint"
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/questions")
                        .headers(new HttpHeaders()))
                        .andReturn()
                        .getResponse()
        List<QuestionDTO> result = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), listType)

        then: "check result with DTO"
        response.status == 200
        verifyAll(result[0]) {
            id == question.id
            author == question.author
            message == question.message
            replies == 0
        }
    }

    /**
     * Scenario: Given no question records, method gets an empty question entity list and returns an empty list.
     */
    def "should return 200 when getting questions with empty list "() {
        given: "prepare response list"
        questionRepository.findAll() >> Collections.emptyList()

        and:
        ObjectMapper objectMapper = new ObjectMapper()
        CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, QuestionDTO.class)

        when: "call get endpoint"
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/questions")
                        .headers(new HttpHeaders()))
                        .andReturn()
                        .getResponse()
        List<QuestionDTO> result = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), listType)

        then: "check result is empty"
        response.status == 200
        result.isEmpty()
    }

    /**
     * Scenario: Given no question records, when bad request is called, method returns an error.
     */
    def "should return 404 when getting questions because request not found"() {
        given:

        when: "call get endpoint"
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/questions123")
                        .headers(new HttpHeaders()))
                        .andReturn()
                        .getResponse()

        then: "expect error response"
        response.status == 404
    }

    /**
     * Scenario: Given a question record with the reply, method gets a specific question entity and returns a question thread DTO.
     */
    def "should return 200 when getting specific question"() {
        given: "prepare response object"
        Question question = Question.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .replies(Collections.singletonList(
                        Reply.builder()
                                .id(2L)
                                .author("Jakub")
                                .message("Text2")
                                .build()))
                .build()
        questionRepository.findById(1L) >> Optional.of(question)

        and:
        ObjectMapper objectMapper = new ObjectMapper()

        when: "call get endpoint"
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/questions/1")
                        .headers(new HttpHeaders()))
                        .andReturn()
                        .getResponse()
        QuestionThreadDTO result = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), QuestionThreadDTO.class)

        then: "check result with DTO"
        response.status == 200
        verifyAll(result) {
            id == question.id
            author == question.author
            message == question.message
            verifyAll(replies.get(0)) {
                id == question.replies.get(0).id
                author == question.replies.get(0).author
                message == question.replies.get(0).message
            }
        }
    }

    /**
     * Scenario: Given no question record, when return response is bad from repository, method returns an error.
     */
    def "should return 500 when getting specific question because bad response object"() {
        given: "prepare response object"
        questionRepository.findById(1L) >> null

        and:

        when: "call get endpoint"
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/questions/1")
                        .headers(new HttpHeaders()))
                        .andReturn()
                        .getResponse()

        then: "expect error response"
        response.status == 500
    }

    /**
     * Scenario: Given no specific question record, when getting a specific question, method returns an error.
     */
    def "should return 404 when getting specific question because not found record"() {
        given: "prepare response object"
        questionRepository.findById(1L) >> Optional.empty()

        and:

        when: "call the get endpoint"
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("/questions/1")
                        .headers(new HttpHeaders()))
                        .andReturn()
                        .getResponse()

        then: "expect the error response"
        response.status == 404
    }

    /**
     * Scenario: Given question records, method creates a question record and returns a question DTO.
     */
    def "should return 201 when posting question"() {
        given: "prepare request"
        Question question = Question.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .build()
        questionRepository.save(_) >> question

        and:
        ObjectMapper objectMapper = new ObjectMapper()

        when: "call get endpoint"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/questions")
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"author\": \"Daniel\", \"message\": \"Text\" }"))
                .andReturn()
                .getResponse()
        QuestionDTO result = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), QuestionDTO.class)

        then: "check result with DTO"
        response.status == 201
        verifyAll(result) {
            id == question.id
            author == question.author
            message == question.message
            replies == 0
        }
    }

    /**
     * Scenario: Given no question records, when author field is missing in the question POST body, method returns an error.
     */
    def "should return 400 when posting question because wrong body"() {
        given: "prepare request list"

        when: "call get endpoint"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/questions")
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"message\": \"Text\" }"))
                .andReturn()
                .getResponse()

        then: "expect the error response"
        response.status == 400
    }

    /**
     * Scenario: Given a specific question record, method creates a reply to the specific question and returns a reply DTO.
     */
    def "should return 201 when posting question reply"() {
        given: "prepare question"
        Question question = Question.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .replies(new ArrayList<Reply>())
                .build()
        questionRepository.findById(1L) >> Optional.of(question)

        and: "prepare saved question"
        Question savedQuestion = Question.builder()
                .id(1L)
                .author("John")
                .message("Text")
                .replies(Collections.singletonList(
                        Reply.builder()
                                .id(2L)
                                .author("Jakub")
                                .message("Text2")
                                .build()))
                .build()

        questionRepository.save(_) >> savedQuestion

        and:
        ObjectMapper objectMapper = new ObjectMapper()

        when: "call get endpoint"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/questions/1/reply")
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"author\": \"Reply author\",\"message\": \"Message reply text\"}"))
                .andReturn()
                .getResponse()
        ReplyDTO result = objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), ReplyDTO.class)

        then: "check result"
        response.status == 201
        verifyAll(result) {
            id == savedQuestion.replies[0].id
            author == savedQuestion.replies[0].author
            message == savedQuestion.replies[0].message
        }
    }

    /**
     * Scenario: Given no question records, when author field is missing in the reply POST body, method returns an error.
     */
    def "should return 400 when posting question reply because of wrong body"() {
        given: "prepare question"

        when: "call get endpoint"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/questions/1/reply")
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\": \"Message reply text\"}"))
                .andReturn()
                .getResponse()

        then: "expect the error response"
        response.status == 400
    }

    /**
     * Scenario: Given no specific question record, when posting a reply to the specific question, method returns an error.
     */
    def "should return 404 when posting question reply because question not found"() {
        given: "prepare question"
        questionRepository.findById(1L) >> Optional.empty()

        when: "call get endpoint"
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/questions/1/reply")
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"author\": \"Reply author\",\"message\": \"Message reply text\"}"))
                .andReturn()
                .getResponse()

        then: "expect the error response"
        response.status == 404
    }

}
