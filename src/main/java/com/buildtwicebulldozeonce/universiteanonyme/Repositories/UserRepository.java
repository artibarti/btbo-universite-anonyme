package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "*")
public interface UserRepository extends CrudRepository<User, Integer>
{
    User findByDoubleHashedPassword(String doubleHashedPassword);
    User findByEmail(String email);

    String courseAdminsQuery =
            "SELECT * FROM user u WHERE u.id IN ( " +
                    "SELECT a.user_id from admin a " +
                        "WHERE a.course_id = :id)";
    @Query(value = courseAdminsQuery, nativeQuery = true)
    Set<User> getCourseAdmins(int id);

}
