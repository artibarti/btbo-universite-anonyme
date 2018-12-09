package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "*")
public interface AnonUserRepository extends CrudRepository<AnonUser, Integer> {
    String courseAdminsQuery =
            "SELECT * FROM anon_user au WHERE au.id IN ( " +
                    "SELECT cs.anon_user_id from course_subs cs " +
                    "WHERE cs.course_id = :id)";

    AnonUser findByAnonNameAndHashedPassword(String anonName, String hashedPassword);

    AnonUser findByAnonName(String anonName);

    @Query(value = courseAdminsQuery, nativeQuery = true)
    Set<AnonUser> getUsersSubbedForCourse(@Param("id") int id);
}
