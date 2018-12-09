package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class QuestionService {

    private static QuestionRepository questionRepository;
    private static RatingRepository ratingRepository;
    private static CommentRepository commentRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, RatingRepository ratingRepository,
                           CommentRepository commentRepository) {
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

    public static void addQuestionForSession(Session session, String question, String token) {

        log.info(String.format("Adding question for user: %s with message %s",UserService.getLoggedInUser(token).getValue2().getAnonName(),question));
        questionRepository.save(Question.builder()
                .session(session)
                .message(question)
                .anonUser(UserService.getLoggedInUser(token).getValue2())
                .timestamp(LocalDateTime.now()).build());
    }

    public static void deleteQuestion(Question question) {
        log.info(String.format("Cleaning up dependencies for question: %s...", question.getId()));

        log.info("Deleting ratings related to the question");
        ratingRepository.getRatingByRefIDAndType(question.getId(), Rating.RatingType.QuestionRating).forEach(RatingService::deleteRating);

        log.info("Deleting comments related to the question");
        commentRepository.getCommentByRefIDAndType(question.getId(), Comment.CommentType.QuestionComment).forEach(CommentService::deleteComment);

        log.info("Deleting question...");
        questionRepository.delete(question);
    }

    public static void updateQuestion(@NonNull Question question) {
        questionRepository.save(question);
    }

    public static Set<Rating> getRatingsForQuestion(int id) {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.QuestionRating);
    }

    public static Set<Comment> getCommentsForQuestion(int id) {
        return commentRepository.getCommentByTypeAndID(id, Comment.CommentType.QuestionComment);
    }

    public static QuestionFatDTO convertToFatDTO(Question question) {
        return new QuestionFatDTO(question.getId(), question.getMessage(), question.getTimestamp(), question.getAnonUser().getAnonName());
    }

    public static Set<Question> getQuestionsForSession(Session session) {

        return new HashSet<>(questionRepository.getQuestionsBySession_Id(session.getId()));
    }

}
