package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface CourseRepository extends CrudRepository<Course, Integer>
{
    @Query(value = "SELECT c FROM Course as c JOIN c.owner as o WHERE o.id = :id")
    Set<Course> getCoursesAdminedByUser(int id);
}
