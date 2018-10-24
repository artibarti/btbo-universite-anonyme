package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return Lists.newArrayList(questionRepository.findAll());
    }

    public Question getQuestion(int id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void addQuestion(@NonNull Question question) {
        questionRepository.save(question);
    }
}
