package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class QuestionService {

    private  static QuestionRepository questionRepository;
    private  static RatingRepository ratingRepository;
    private  static CommentRepository commentRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, RatingRepository ratingRepository,
                           CommentRepository commentRepository)
    {
        QuestionService.questionRepository = questionRepository;
        QuestionService.ratingRepository = ratingRepository;
        QuestionService.commentRepository = commentRepository;
    }

    public static List<Question> getAllQuestions() {
        return Lists.newArrayList(questionRepository.findAll());
    }

    public static Question getQuestion(int id) {
        return questionRepository.findById(id).orElse(null);
    }

    public static void addQuestionForSession(int id, @NonNull Question question)
    {

    }

    public static void deleteQuestion(int id)
    {
        questionRepository.deleteById(id);
    }

    public static void deleteQuestion(Question question)
    {
        questionRepository.delete(question);
    }

    public static void deleteQuestionsBySession(Session session)
    {
        deleteQuestionsBySessionId(session.getId());
        log.info("Successfully delete every question for session => " + session.getName());
    }

    public static void deleteQuestionsBySessionId(int id)
    {
        questionRepository.getQuestionsForSession(id).forEach(QuestionService::deleteQuestion);
    }

    public static void updateQuestion(@NonNull Question question)
    {
        questionRepository.save(question);
    }

    public static Set<Rating> getRatingsForQuestion(int id)
    {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.QuestionRating);
    }

    public static Set<Comment> getCommentsForQuestion(int id)
    {
        return commentRepository.getCommentByTypeAndID(id, Comment.CommentType.QuestionComment);
    }

}
