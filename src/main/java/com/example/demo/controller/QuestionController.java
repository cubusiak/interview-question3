package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionThreadDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Spring rest controller, maps web request with starting path /questions.
 * Controller allows to add a new question and a new reply to the existing question;
 * get all the questions and get a specific question with all the replied messages.
 */
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    /**
     * GET request, gives list of all questions and returns status 200 OK
     *
     * @return QuestionDTO List
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionDTO> getAllQuestions() {
        return service.getAllQuestions();
    }

    /**
     * GET request, gives a question with all the replied messages and returns status 200 OK
     *
     * @param id is a number with questionId
     * @return QuestionThreadDTO
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionThreadDTO getQuestionThreadById(@PathVariable("id") Long id) {
        return service.getQuestionThreadById(id);
    }

    /**
     * POST request, takes a question object, saves it in the database,
     * returns question object with the id and status 201 CREATED
     *
     * @param questionDTO is a question object which will be converted to entity and saved to database
     * @return QuestionDTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDTO createQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
        return service.createQuestion(questionDTO);
    }

    /**
     * POST request, takes a reply object and id of the question, saves reply in the database,
     * returns reply object with the id and status 201 CREATED
     *
     * @param replyDTO is a reply object which will be converted to entity and saved to database
     * @param id       is a number with questionId
     * @return ReplyDTO
     */
    @PostMapping("/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public ReplyDTO createReply(@RequestBody @Valid ReplyDTO replyDTO, @PathVariable("id") Long id) {
        return service.createReply(replyDTO, id);
    }

}
