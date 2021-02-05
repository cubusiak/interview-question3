package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionThreadDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Reply;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Question service, provides logical operations for GET and POST rest methods.
 */
@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionConverter converter;

    public QuestionService(QuestionRepository repository, QuestionConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * Method finds all questions, converts question entity to DTO, return list of questions.
     *
     * @return QuestionDTO List
     */
    public List<QuestionDTO> getAllQuestions() {
        return repository.findAll()
                .stream()
                .map(converter::convertToQuestionDTO)
                .collect(Collectors.toList());
    }

    /**
     * Method finds a specific question, converts QuestionThread entity to DTO, return DTO.
     *
     * @param id is a number with questionId
     * @return QuestionThreadDTO
     * @throws RecordNotFoundException is thrown when specific question is not found
     */
    public QuestionThreadDTO getQuestionThreadById(Long id) {
        Question question = repository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
        return converter.convertToQuestionThreadDTO(question);
    }

    /**
     * Method converts question DTO to entity, saves it in the repository, converts question to DTO, returns question DTO.
     *
     * @param questionDTO is a DTO to be saved
     * @return QuestionDTO
     */
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question question = converter.convertToQuestion(questionDTO);
        return converter.convertToQuestionDTO(repository.save(question));
    }

    /**
     * Method converts reply DTO to entity, finds question by questionId,
     * saves reply in the repository, converts reply to DTO, returns reply DTO.
     *
     * @param replyDTO is a reply object which will be converted to entity and saved to database
     * @param id       is a number with questionId
     * @return ReplyDTO
     * @throws RecordNotFoundException is thrown when specific question is not found
     */
    public ReplyDTO createReply(ReplyDTO replyDTO, Long id) {
        Reply reply = converter.convertToReply(replyDTO);
        Question question = repository.findById(id)
                .orElseThrow(RecordNotFoundException::new);

        reply.setQuestion(question);
        question.getReplies().add(reply);
        Question result = repository.save(question);
        return converter.convertToReplyDTO(getReplyFromQuestion(result), result.getId());
    }

    /**
     * Method gets the last element of the reply list.
     *
     * @param result is a question entity
     * @return Reply
     */
    private Reply getReplyFromQuestion(Question result) {
        return result.getReplies().get(result.getReplies().size() - 1);
    }

}
