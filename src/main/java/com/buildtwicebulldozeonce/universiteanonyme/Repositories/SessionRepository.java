package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SessionRepository extends CrudRepository<Session, Integer>
{
    @Query("SELECT q FROM Question as q JOIN q.session as s WHERE s.id = :id")
    Set<Question> getQuestionsForSession(@Param("id") int id);

    // TODO: getRatingsForSession

}
