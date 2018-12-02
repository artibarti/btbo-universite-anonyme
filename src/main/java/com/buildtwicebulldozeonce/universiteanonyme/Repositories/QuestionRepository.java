package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Set;

@CrossOrigin(origins = "*")
public interface QuestionRepository extends CrudRepository<Question, Integer>
{
    @Query("SELECT q FROM Question as q JOIN q.session as s WHERE s.id = :id")
    Set<Question> getQuestionsForSession(@Param("id") int id);

    String newsFeedQuestionsQuery =
            "SELECT * from question q " +
                    "WHERE q.session_id IN (SELECT s.id FROM session s " +
                        "WHERE s.course_id IN (SELECT cs.course_id FROM course_subs cs " +
                        "WHERE cs.anon_user_id = :id)) " +
                    "AND q.anon_user_id != :id " +
                    "ORDER BY q.timestamp " +
                    "LIMIT 20";
    @Query(value = newsFeedQuestionsQuery, nativeQuery = true)
    Set<Question> getNewsFeedQuestionsForUser(@Param("id") int id);

    String questionsForCourseQuery =
            "SELECT * from question q " +
                    "WHERE q.session_id IN (" +
                    "SELECT s.id FROM session s " +
                    "WHERE s.course_id = :id)";
    @Query(value = questionsForCourseQuery, nativeQuery = true)
    Set<Question> getQuestionsForCourse(@Param("id") int id);
}
