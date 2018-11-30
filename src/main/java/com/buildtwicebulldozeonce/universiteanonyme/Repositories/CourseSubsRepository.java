package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseSubs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "*")
public interface CourseSubsRepository extends CrudRepository<CourseSubs, Integer>
{
    @Query(value = "SELECT cs FROM CourseSubs as cs JOIN cs.anonUser as au WHERE au.id = :id")
    Set<CourseSubs> getCourseSubsForAnonUser(@Param("id") int id);

    @Query(value = "SELECT cs FROM CourseSubs as cs JOIN cs.course as c WHERE c.id = :id")
    Set<CourseSubs> getCourseSubsForCourse(@Param("id") int id);

}
