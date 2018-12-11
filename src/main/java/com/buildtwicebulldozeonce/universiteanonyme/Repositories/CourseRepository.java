package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "*")
public interface CourseRepository extends CrudRepository<Course, Integer> {
    String subsForUserQuery =
            "SELECT * FROM course c WHERE c.id IN (" +
                    "SELECT cs.course_id FROM course_subs cs " +
                    "WHERE cs.anon_user_id = :anonID)";
    String hotCoursesQuery =
            "SELECT * FROM course c WHERE c.hidden IS false " +
                    "AND c.id NOT IN " +
                    "(SELECT cs.course_id FROM course_subs cs WHERE cs.anon_user_id = :anonID) " +
                    "AND c.owner_id != :userId " +
                    "LIMIT 20";
    String courseByInviteCodeQuery =
            "SELECT * FROM course c WHERE c.id = " +
                    "(SELECT course_id FROM invite_code WHERE code LIKE :code)";

    @Query(value = "SELECT * FROM course c WHERE c.owner_id = :id", nativeQuery = true)
    Set<Course> getCoursesAdminedByUser(@Param("id") int id);

    @Query(value = subsForUserQuery, nativeQuery = true)
    Set<Course> getSubscriptionsForUser(@Param("anonID") int anonID);

    @Query(value = hotCoursesQuery, nativeQuery = true)
    Set<Course> getHotCourses(@Param("anonID") int anonID,@Param("userId") int userId);

    @Query(value = courseByInviteCodeQuery, nativeQuery = true)
    Course findByInviteCode(@Param("code") String code);
}
