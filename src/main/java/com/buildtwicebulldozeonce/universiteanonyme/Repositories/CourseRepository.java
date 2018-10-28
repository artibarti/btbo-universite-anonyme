package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CourseRepository extends CrudRepository<Course, Integer>
{

    @Query(value = "SELECT a FROM Admin as a JOIN a.course as c WHERE c.id = :id")
    Set<Admin> getCourseAdmins(@Param("id") int id);

    @Query(value = "SELECT cs FROM CourseSubs as cs JOIN cs.course as c WHERE c.id = :id")
    Set<CourseSubs> getCourseSubs(@Param("id") int id);

    @Query(value = "SELECT cr FROM CourseRoom as cr JOIN cr.course as c WHERE c.id = :id")
    Set<CourseRoom> getCourseRooms(@Param("id") int id);

    @Query("SELECT s FROM Session as s JOIN s.course as c WHERE c.id = :id")
    Set<Session> getSessionsForCourse(@Param("id") int id);

    // TODO: getRatingsForCourse

}
