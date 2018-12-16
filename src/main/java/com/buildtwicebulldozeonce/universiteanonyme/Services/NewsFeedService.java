package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.News;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NewsFeedService {
    private static CommentRepository commentRepository;
    private static QuestionRepository questionRepository;
    private static RatingRepository ratingRepository;
    private static CourseRepository courseRepository;

    @Autowired
    public NewsFeedService(CommentRepository commentRepository, QuestionRepository questionRepository,
                           RatingRepository ratingRepository, CourseRepository courseRepository) {
        NewsFeedService.commentRepository = commentRepository;
        NewsFeedService.questionRepository = questionRepository;
        NewsFeedService.ratingRepository = ratingRepository;
        NewsFeedService.courseRepository = courseRepository;
    }

    private static News convertRatingToNews(Rating rating) {
        News news = new News();
        news.setRefID(rating.getRefID());
        news.setAnonUserName(rating.getAnonUser().getAnonName());
        news.setType(rating.getType().toString());
        news.setTimestamp(rating.getTimestamp());
        return news;
    }

    private static News convertCommentToNews(Comment comment) {
        News news = new News();
        news.setTimestamp(comment.getTimestamp());
        news.setType(comment.getType().toString());

        if (comment.getAnonUser() != null)
            news.setAnonUserName("Anonymous");
        else
            news.setAnonUserName(comment.getUser().getFirstName() + " " + comment.getUser().getLastName());

        news.setContent(comment.getMessage());
        news.setRefID(comment.getRefID());
        return news;
    }

    private static News convertQuestionToNews(Question question) {
        News news = new News();
        news.setTimestamp(question.getTimestamp());
        news.setType("Question");
        news.setAnonUserName(question.getAnonUser().getAnonName());
        news.setContent(question.getMessage());
        news.setRefID(question.getSession().getId());
        news.setRefName(question.getSession().getCourse().getName());
        return news;
    }

    public static List<News> getNewsFeedForUser(int id, int anonID) {
        List<News> news = new ArrayList<>();

        Set<Comment> comments = commentRepository.getNewsFeedCommentsForUser(id, anonID);
        Set<Question> questions = questionRepository.getNewsFeedQuestionsForUser(anonID);
        Set<Rating> ratings = ratingRepository.getNewsFeedRatingsForUser(id, anonID);

        log.info(String.format("Number of comments for user: %s \nNumber of questions for user: %s\nNumber of Ratings for user: %s",comments.size(),questions.size(),ratings.size()));

        comments
                .forEach(p -> news.add(convertCommentToNews(p)));
        questions
                .forEach(p -> news.add(convertQuestionToNews(p)));
        ratings
                .forEach(p -> news.add(convertRatingToNews(p)));

        return news.stream()
                .sorted(Comparator.comparing(News::getTimestamp).reversed())
                .collect(Collectors.toList());
    }


}
