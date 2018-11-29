package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, RatingRepository ratingRepository,
                           CommentRepository commentRepository)
    {
        this.questionRepository = questionRepository;
        this.ratingRepository = ratingRepository;
        this.commentRepository = commentRepository;
    }

    public List<Question> getAllQuestions() {
        return Lists.newArrayList(questionRepository.findAll());
    }

    public Question getQuestion(int id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void addQuestionForSession(int id, @NonNull Question question)
    {

    }

    public void deleteQuestion(int id)
    {
        questionRepository.deleteById(id);
    }

    public void updateQuestion(@NonNull Question question)
    {
        questionRepository.save(question);
    }

    public Set<Rating> getRatingsForQuestion(int id)
    {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.QuestionRating);
    }

    public Set<Comment> getCommentsForQuestion(int id)
    {
        return commentRepository.getCommentByTypeAndID(id, Comment.CommentType.QuestionComment);
    }

}
