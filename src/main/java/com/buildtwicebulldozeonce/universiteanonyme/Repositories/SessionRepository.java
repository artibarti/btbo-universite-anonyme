package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface SessionRepository extends CrudRepository<Session, Integer>
{
    @Query("SELECT s FROM Session as s JOIN s.course as c WHERE c.id = :id")
    Set<Session> getSessionsForCourse(@Param("id") int id);

}
