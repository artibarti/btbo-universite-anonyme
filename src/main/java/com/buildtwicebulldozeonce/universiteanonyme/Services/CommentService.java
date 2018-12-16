package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRoomCommentDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
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
        LoggedInUser loggedInUser = LoggedInUserService.getLoggedInUser(token);
        Comment comment = getComment(commentId);
        Rating rating;

        if(isCommentRatedByUser(loggedInUser.getAnonUser(), comment))
        {
            rating = getRating(loggedInUser.getAnonUser(), comment);
            rating.setValue(value);
            rating.setTimestamp(LocalDateTime.now());
        }
        else
        {
            rating = Rating.builder()
                    .anonUser(loggedInUser.getAnonUser())
                    .timestamp(LocalDateTime.now())
                    .type(Rating.RatingType.CommentRating)
                    .refID(commentId)
                    .value(value)
                    .build();
        }
        log.trace("Comment: " + comment.getMessage() + " has been rated by: " +
                loggedInUser.getAnonUser().getAnonName() + " with value: " + value);
        RatingService.saveRating(rating);
    }

    public static CourseRoomCommentDTO convertToCourseRoomCommentDTO(Comment comment, String token)
    {
        AnonUser anonUser = LoggedInUserService.getLoggedInUser(token).getAnonUser();

        return new CourseRoomCommentDTO(
                comment.getId(),
                comment.getMessage(),
                CommentService.isCommentRatedByUser(anonUser,comment),
                comment.getAnonUser() != null ? "Anonymous" : comment.getUser().getFirstName() + " " + comment.getUser().getLastName(),
                comment.getTimestamp());
    }
}
