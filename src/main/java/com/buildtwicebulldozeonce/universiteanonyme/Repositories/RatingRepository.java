package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface RatingRepository extends CrudRepository<Rating, Integer>
{

    String newsFeedRatingsQuery =
            "SELECT * FROM rating r WHERE r.refid IN ( " +
                    "(SELECT c.id from course c " +
                        "WHERE c.id IN (SELECT a.course_id FROM admin a " +
                            "WHERE a.user_id = :id) AND " +
                    "r.type = :course)) UNION " +
            "SELECT * FROM rating r WHERE r.refid IN ( " +
                    "(SELECT q.id FROM question q " +
                        "WHERE q.anon_user_id = :id) AND " +
                        "r.type = :question) UNION " +
            "SELECT * FROM rating r WHERE r.refid IN ( " +
                    "SELECT c.id FROM comment c " +
                         "WHERE c.anon_user_id = :id AND " +
                         "r.type = :comment)";
                    @Query(value = newsFeedRatingsQuery, nativeQuery = true)
    Set<Rating> getNewsFeedRatingsForUser(
            @Param("id") int id,
            @Param("course") Rating.RatingType course,
            @Param("comment") Rating.RatingType comment,
            @Param("question") Rating.RatingType question
    );
}
