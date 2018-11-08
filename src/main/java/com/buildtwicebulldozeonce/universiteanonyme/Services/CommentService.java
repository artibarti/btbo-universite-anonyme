package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return Lists.newArrayList(commentRepository.findAll());
    }

    public Comment getComment(int id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void addComment(@NonNull Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(int id)
    {
        commentRepository.deleteById(id);
    }

    public void updateComment(@NonNull Comment comment)
    {
        commentRepository.save(comment);
    }

    public Set<Rating> getRatingsForComment(int id)
    {
        return commentRepository.getRatingsForComment(id, Rating.RatingType.CommentRating);
    }

}
