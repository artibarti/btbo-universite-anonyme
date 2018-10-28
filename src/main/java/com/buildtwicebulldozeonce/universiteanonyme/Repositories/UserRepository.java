package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer>
{

    @Query("SELECT a FROM Admin as a JOIN a.user as u WHERE u.id = :userID")
    public Iterable<Admin> getAdminRolesForUser(@Param("userID") int userID);

    @Query("SELECT c FROM Comment as c JOIN c.user as u WHERE u.id = :userID")
    public Iterable<Comment> getAllCommentsFromUser(@Param("userID") int userID);

    @Query("SELECT c FROM Course as c JOIN c.owner as o WHERE o.id = :userID")
    public Iterable<Course> getCoursesCreatedByUser(@Param("userID") int userID);

    @Query("SELECT q FROM Question as q JOIN q.anonUser as au WHERE au.id = :anonID")
    public Iterable<Question> getAllQuestionsFromUser(@Param("anonID") int anonID);

    @Query("SELECT r FROM Rating as r JOIN r.user as u WHERE u.id = :userID")
    public Iterable<Rating> getAllRatingsFromUser(@Param("userID") int userID);


}
