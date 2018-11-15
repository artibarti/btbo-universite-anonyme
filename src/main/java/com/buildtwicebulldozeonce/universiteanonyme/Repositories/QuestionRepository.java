package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface QuestionRepository extends CrudRepository<Question, Integer>
{
    @Query("SELECT r FROM Rating as r WHERE r.refID = :id AND r.type = :type")
    Set<Rating> getRatings(@Param("id") int id, @Param("type") Rating.RatingType type);

    @Query("SELECT m FROM Comment as m WHERE m.refID = :id AND m.type = :type")
    Set<Comment> getComments(@Param("id") int id, @Param("type") Comment.CommentType type);
}
