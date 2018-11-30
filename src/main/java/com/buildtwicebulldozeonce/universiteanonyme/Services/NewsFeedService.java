package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.News;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsFeedService
{
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public NewsFeedService(CommentRepository commentRepository, QuestionRepository questionRepository,
                           RatingRepository ratingRepository, CourseRepository courseRepository)
    {
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
        this.ratingRepository = ratingRepository;
        this.courseRepository = courseRepository;
    }

    private News convertRatingToNews(Rating rating)
    {
        News news = new News();
        news.setRefID(rating.getRefID());
        news.setAnonUserName(rating.getAnonUser().getAnonName());
        news.setType(rating.getType().toString());
        news.setTimestamp(rating.getTimestamp());
        return news;
    }

    private News convertCommentToNews(Comment comment)
    {
        News news = new News();
        news.setTimestamp(comment.getTimestamp());
        news.setType(comment.getType().toString());
        news.setAnonUserName(comment.getAnonUser().getAnonName());
        news.setContent(comment.getMessage());
        news.setRefID(comment.getRefID());
        return news;
    }

    private News convertQuestionToNews(Question question)
    {
        News news = new News();
        news.setTimestamp(question.getTimestamp());
        news.setType("Question");
        news.setAnonUserName(question.getAnonUser().getAnonName());
        news.setContent(question.getMessage());
        news.setRefID(question.getSession().getId());
        news.setRefName(question.getSession().getCourse().getName());
        return news;
    }

    public List<News> getNewsFeedForUser(int id)
    {
        List<News> news = new ArrayList<>();

        Set<Comment> comments = commentRepository.getNewsFeedCommentsForUser(id);
        Set<Question> questions = questionRepository.getNewsFeedQuestionsForUser(id);
        Set<Rating> ratings = ratingRepository.getNewsFeedRatingsForUser(id);

        comments.stream()
            .forEach(p -> news.add(convertCommentToNews(p)));
        questions.stream()
            .forEach(p -> news.add(convertQuestionToNews(p)));
        ratings.stream()
            .forEach(p -> news.add(convertRatingToNews(p)));

        return news.stream()
            .sorted(Comparator.comparing(News::getTimestamp).reversed())
            .collect(Collectors.toList());
    }


}
