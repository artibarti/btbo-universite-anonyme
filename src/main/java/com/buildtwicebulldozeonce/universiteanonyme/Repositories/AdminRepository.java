package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "*")
public interface AdminRepository extends CrudRepository<Admin, Integer> {
    @Query(value = "SELECT a FROM Admin as a JOIN a.user as u WHERE u.id = :userID")
    Set<Admin> getAdminRolesForUser(@Param("userID") int userID);

    @Query(value = "SELECT a FROM Admin as a JOIN a.course as c WHERE c.id = :id")
    Set<Admin> getCourseAdmins(@Param("id") int id);
}
