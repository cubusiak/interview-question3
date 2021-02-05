package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionThreadDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.model.Question;
import com.example.demo.model.Reply;
import com.example.demo.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionConverter converter;

    public QuestionService(QuestionRepository repository, QuestionConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public List<QuestionDTO> getAllQuestions() {
        return repository.findAll()
                .stream()
                .map(converter::convertToReplyDTO)
                .collect(Collectors.toList());
    }

    public QuestionThreadDTO getQuestionThreadById(Long id) {
        Question question = repository.findById(id)
                .orElseThrow(RecordNotFoundException::new);
        return converter.convertToQuestionThreadDTO(question);
    }

    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question question = converter.convertToQuestion(questionDTO);
        return converter.convertToReplyDTO(repository.save(question));
    }

    public ReplyDTO createReply(ReplyDTO replyDTO, Long id) {
        Reply reply = converter.convertToReply(replyDTO);
        Question question = repository.findById(id)
                .orElseThrow(RecordNotFoundException::new);

        reply.setQuestion(question);
        question.getReplies().add(reply);
        Question result = repository.save(question);
        return converter.convertToReplyDTO(getReplyFromQuestion(result), result.getId());
    }

    private Reply getReplyFromQuestion(Question result) {
        return result.getReplies().get(result.getReplies().size() - 1);
    }

}
