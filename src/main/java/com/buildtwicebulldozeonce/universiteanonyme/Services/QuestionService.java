package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public void deleteQuestion(int id)
    {
        questionRepository.deleteById(id);
    }

    public void updateQuestion(@NonNull Question question)
    {
        questionRepository.save(question);
    }

    public Set<Rating> getRatings(int id)
    {
        return questionRepository.getRatings(id, Rating.RatingType.QuestionRating);
    }

    public Set<Comment> getComments(int id)
    {
        return questionRepository.getComments(id, Comment.CommentType.QuestionComment);
    }

}
