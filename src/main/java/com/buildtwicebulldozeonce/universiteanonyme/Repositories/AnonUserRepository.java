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
    AnonUser findByAnonNameAndHashedPassword(String anonName, String hashedPassword);
    AnonUser findByAnonName(String anonName);
}
