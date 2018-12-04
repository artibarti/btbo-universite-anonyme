package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "*")
public interface CourseRepository extends CrudRepository<Course, Integer>
{
    @Query(value = "SELECT * FROM course c WHERE c.owner_id = :id", nativeQuery = true)
    Set<Course> getCoursesAdminedByUser(@Param("id") int id);

    String subsForUserQuery =
            "SELECT * FROM course c WHERE c.id IN (" +
                    "SELECT cs.id FROM course_subs cs " +
                        "WHERE cs.anon_user_id = :anonID)";
    @Query(value = subsForUserQuery, nativeQuery = true)
    Set<Course> getSubscriptionsForUser(@Param("anonID") int anonID);
}
