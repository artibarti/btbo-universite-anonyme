package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseSubs;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface AnonUserRepository extends CrudRepository<AnonUser, Integer>
{
    @Query(value = "SELECT c FROM Comment as c JOIN c.user as u WHERE u.id = :id")
    Set<Comment> getAllCommentsFromAnonUser(@Param("id") int is);

    @Query(value = "SELECT cs FROM CourseSubs as cs JOIN cs.anonUser as au WHERE au.id = :id")
    Set<CourseSubs> getCourseSubsForAnonUser(@Param("id") int id);

    @Query(value = "SELECT q FROM Question as q JOIN q.anonUser as au WHERE au.id = :id")
    Set<Question> getAllQuestionsFromAnonUser(@Param("id") int anonID);

    AnonUser findByAnonNameAndHashedPassword(String anonName, String hashedPassword);

    AnonUser findByAnonName(String anonName);
}
