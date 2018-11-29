package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface CourseRoomRepository extends CrudRepository<CourseRoom, Integer> {

    @Query(value = "SELECT cr FROM CourseRoom as cr JOIN cr.course as c WHERE c.id = :id")
    Set<CourseRoom> getCourseRoomsForCourse(@Param("id") int id);

}
