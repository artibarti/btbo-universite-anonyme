package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
public class CommentService {

    private static CommentRepository commentRepository;
    private static RatingRepository ratingRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, RatingRepository ratingRepository) {
        CommentService.commentRepository = commentRepository;
        CommentService.ratingRepository = ratingRepository;
    }

    public static Comment getComment(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    public static void addComment(@NonNull Comment comment) {
        commentRepository.save(comment);
    }

    public static void deleteComment(Comment comment) {
        log.info(String.format("Deleting ratings related to comment with id: %s...",comment.getId()));
        ratingRepository.getRatingByRefIDAndType(comment.getId(), Rating.RatingType.CommentRating).forEach(RatingService::deleteRating);

        log.info("Deleting comment...");
        commentRepository.delete(comment);
    }

    public static void updateComment(@NonNull Comment comment) {
        commentRepository.save(comment);
    }

    public static Set<Rating> getRatingsForComment(int id) {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.CommentRating);
    }

    public static boolean isCommentRatedByUser(AnonUser anonUser, Comment comment) {
        return getRating(anonUser, comment) != null;
    }

    public static Rating getRating(AnonUser anonUser, Comment comment) {
        return RatingService.getRating(anonUser, Rating.RatingType.CommentRating, comment.getId());
    }

    public static void addRating(String token, int value, int commentId) {
        Triplet<String, User, AnonUser> loggedInUser = UserService.getLoggedInUser(token);
        Comment comment = getComment(commentId);
        Rating rating;

        if(isCommentRatedByUser(loggedInUser.getValue2(), comment))
        {
            rating = getRating(loggedInUser.getValue2(), comment);
            rating.setValue(value);
            rating.setTimestamp(LocalDateTime.now());
        }
        else
        {
            rating = Rating.builder()
                    .anonUser(loggedInUser.getValue2())
                    .timestamp(LocalDateTime.now())
                    .type(Rating.RatingType.CommentRating)
                    .refID(commentId)
                    .value(value)
                    .build();
        }
        log.trace("Comment: " + comment.getMessage() + " has been rated by: " +
                loggedInUser.getValue2().getAnonName() + " with value: " + value);
        RatingService.saveRating(rating);
    }
}
