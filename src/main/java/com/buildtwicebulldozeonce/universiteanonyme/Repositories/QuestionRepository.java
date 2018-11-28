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

    String newsFeedQuestionsQuery =
            "SELECT * from question q " +
                    "WHERE q.session_id IN (SELECT s.id FROM session s " +
                    "WHERE s.course_id IN (SELECT cs.course_id FROM course_subs cs " +
                    "WHERE cs.anon_user_id = :id))" +
                    "ORDER BY q.timestamp " +
                    "LIMIT 20";
    @Query(value = newsFeedQuestionsQuery, nativeQuery = true)
    Set<Question> getNewsFeedQuestionsForUser(@Param("id") int id);
}
