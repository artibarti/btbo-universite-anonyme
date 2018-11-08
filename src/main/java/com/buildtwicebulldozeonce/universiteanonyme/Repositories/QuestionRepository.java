package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface QuestionRepository extends CrudRepository<Question, Integer>
{
    @Query("SELECT r FROM Rating as r WHERE r.refID = :id AND r.type = :type")
    Set<Rating> getRatingsForQuestion(@Param("id") int id, @Param("type") Rating.RatingType type);

    @Query("SELECT c FROM Comment as c JOIN c.question as q WHERE q.id = :id")
    Set<Comment> getCommentsForQuestion(@Param("id") int id);
}
