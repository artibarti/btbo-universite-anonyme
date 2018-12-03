package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.InviteCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "*")
public interface InviteCodeRepository  extends CrudRepository<InviteCode, Integer>
{
    @Query(value = "SELECT ic FROM InviteCode as ic JOIN ic.course as c WHERE c.id = :courseID")
    Set<InviteCode> getAllInviteCodesForCourse(@Param("courseID") int courseID);
}
