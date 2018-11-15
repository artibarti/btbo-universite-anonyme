package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
public interface CourseRoomRepository extends CrudRepository<CourseRoom, Integer> {

    @Query("SELECT m FROM Comment as m WHERE m.refID = :id AND m.type = :type")
    Set<Comment> getComments(int id, Comment.CommentType type);
}
