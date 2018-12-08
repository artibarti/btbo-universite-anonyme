package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    private  static CommentRepository commentRepository;
    private  static RatingRepository ratingRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, RatingRepository ratingRepository) {
        CommentService.commentRepository = commentRepository;
        CommentService.ratingRepository = ratingRepository;
    }

    public static Comment getComment(int id)
    {
        return commentRepository.findById(id).orElse(null);
    }

    public static void addComment(@NonNull Comment comment)
    {
        commentRepository.save(comment);
    }

    public static void deleteComment(int id)
    {
        commentRepository.deleteById(id);
    }

    public static void updateComment(@NonNull Comment comment)
    {
        commentRepository.save(comment);
    }

    public static Set<Rating> getRatingsForComment(int id)
    {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.CommentRating);
    }
}
