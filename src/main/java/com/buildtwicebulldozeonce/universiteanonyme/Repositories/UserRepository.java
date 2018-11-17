package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends CrudRepository<User, Integer>
{

    @Query(value = "SELECT a FROM Admin as a JOIN a.user as u WHERE u.id = :userID")
    Set<Admin> getAdminRolesForUser(@Param("userID") int userID);

    @Query(value = "SELECT c FROM Comment as c JOIN c.user as u WHERE u.id = :userID")
    Set<Comment> getAllCommentsFromUser(@Param("userID") int userID);

    @Query(value = "SELECT c FROM Course as c JOIN c.owner as o WHERE o.id = :userID")
    Set<Course> getCoursesCreatedByUser(@Param("userID") int userID);

    @Query(value = "SELECT q FROM Question as q JOIN q.anonUser as au WHERE au.id = :anonID")
    Set<Question> getAllQuestionsFromUser(@Param("anonID") int anonID);

    @Query(value = "SELECT r FROM Rating as r JOIN r.user as u WHERE u.id = :userID")
    Set<Rating> getAllRatingsFromUser(@Param("userID") int userID);

    User findByEmailAndDoubleHashedPassword(String name, String password);

}
