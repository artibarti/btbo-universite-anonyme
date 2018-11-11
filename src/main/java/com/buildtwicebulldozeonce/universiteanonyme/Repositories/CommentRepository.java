package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CommentRepository extends CrudRepository<Comment, Integer>
{

    @Query("SELECT r FROM Rating as r WHERE r.refID = :id AND r.type = :type")
    Set<Rating> getRatingsForComment(@Param("id") int id, @Param("type") Rating.RatingType type);
}
