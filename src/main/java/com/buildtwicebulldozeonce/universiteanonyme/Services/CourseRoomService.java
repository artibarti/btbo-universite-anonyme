package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRoomRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CourseRoomService {

    private final CourseRoomRepository courseRoomRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CourseRoomService(CourseRoomRepository courseRoomRepository, CommentRepository commentRepository) {
        this.courseRoomRepository = courseRoomRepository;
        this.commentRepository = commentRepository;
    }

    public CourseRoom getCourseRoom(int id)
    {
        return courseRoomRepository.findById(id).orElse(null);
    }

    public void addCourseRoom(@NonNull CourseRoom courseRoom)
    {
        courseRoomRepository.save(courseRoom);
    }

    public Set<Comment> getCommentsForCourseRoom(int id)
    {
        return commentRepository.getCommentByTypeAndID(id, Comment.CommentType.CourseRoomComment);
    }

    public void updateCourseRoom(CourseRoom courseRoom)
    {
        courseRoomRepository.save(courseRoom);
    }

    public void deleteCourseRoom(int id)
    {
        courseRoomRepository.deleteById(id);
    }
}
