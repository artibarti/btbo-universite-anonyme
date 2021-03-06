package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
public interface CommentRepository extends CrudRepository<Comment, Integer> {

    String newsFeedCommentsQuery =
            "SELECT * FROM comment c WHERE c.refid IN (" +
                    "SELECT cr.id from course_room cr " +
                    "WHERE cr.course_id IN (SELECT cs.course_id FROM course_subs cs " +
                    "WHERE cs.anon_user_id = :anonID) " +
                    "AND c.type = \'CourseRoomComment\' " +
                    "AND (( c.anon_user_id IS NOT NULL AND c.anon_user_id != :anonID) " +
                    "OR ( c.user_id IS NOT NULL AND c.user_id != :id )) UNION " +
                    "SELECT cr.id FROM course_room cr " +
                    "WHERE cr.course_id IN (SELECT c.id FROM course c WHERE c.owner_id = :id) " +
                    "AND (( c.anon_user_id IS NOT NULL AND c.anon_user_id != :anonID) " +
                    "OR ( c.user_id IS NOT NULL AND c.user_id != :id )) UNION " +
                    "SELECT q.id from question q " +
                    "WHERE q.session_id IN (SELECT s.id FROM session s " +
                    "WHERE s.course_id IN (SELECT cs.course_id FROM course_subs cs " +
                    "WHERE cs.anon_user_id = :anonID)) " +
                    "OR q.session_id IN (SELECT s.id FROM session s " +
                    "WHERE s.course_id IN (SELECT c.id FROM course c WHERE c.owner_id = :id)) " +
                    "AND c.type = \'QuestionComment\' " +
                    "AND c.anon_user_id != :anonID " +
                    "AND c.user_id != :id) " +
                    "ORDER BY c.timestamp " +
                    "LIMIT 20";

    String commentsForCourseQuery =
            "SELECT * FROM comment c WHERE c.refid IN (" +
                    "SELECT cr.id from course_room cr " +
                    "WHERE cr.course_id = :id " +
                    "AND c.type = \'CourseRoomComment\' UNION " +
                    "SELECT q.id from question q " +
                    "WHERE q.session_id IN (SELECT s.id FROM session s " +
                    "WHERE s.course_id = :id) " +
                    "AND c.type = \'QuestionComment\')";


    @Query("SELECT c FROM Comment as c WHERE c.refID = :id AND c.type = :type")
    List<Comment> getCommentByTypeAndID(@Param("id") int id, @Param("type") Comment.CommentType type);

    @Query(value = newsFeedCommentsQuery, nativeQuery = true)
    Set<Comment> getNewsFeedCommentsForUser(@Param("id") int id, @Param("anonID") int anonID);

    @Query(value = commentsForCourseQuery, nativeQuery = true)
    Set<Comment> getCommentsForCourse(@Param("id") int id);

    Set<Comment> getCommentByRefIDAndType(int refID, Comment.CommentType type);

    Set<Comment> getCommentsByUserOrAnonUser(User user, AnonUser anonUser);
}
