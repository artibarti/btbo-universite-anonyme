package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRoomRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CourseRoomService {

    private  static CourseRoomRepository courseRoomRepository;
    private  static CommentRepository commentRepository;

    @Autowired
    public CourseRoomService(CourseRoomRepository courseRoomRepository, CommentRepository commentRepository) {
        CourseRoomService.courseRoomRepository = courseRoomRepository;
        CourseRoomService.commentRepository = commentRepository;
    }

    public static CourseRoom getCourseRoom(int id)
    {
        return courseRoomRepository.findById(id).orElse(null);
    }

    public static void addCourseRoom(@NonNull CourseRoom courseRoom)
    {
        courseRoomRepository.save(courseRoom);
    }

    public static Set<Comment> getCommentsForCourseRoom(int id)
    {
        return commentRepository.getCommentByTypeAndID(id, Comment.CommentType.CourseRoomComment);
    }

    public static void updateCourseRoom(CourseRoom courseRoom)
    {
        courseRoomRepository.save(courseRoom);
    }

    public static void deleteCourseRoom(int id)
    {
        CommentService.deleteCommentsByCourseRoomId(id);
        courseRoomRepository.deleteById(id);
        log.info("Successfully deleted course room by id => " + id);
    }

    public static void deleteCourseRoom(CourseRoom courseRoom)
    {
        CommentService.deleteCommentsByCourseRoomId(courseRoom.getId());
        courseRoomRepository.delete(courseRoom);
        log.info("Successfully deleted course room => " + courseRoom.getName());
    }

    public static void deleteCourseRoomsForCourse(int id)
    {
        CourseService.getCourseRooms(id).forEach(CourseRoomService::deleteCourseRoom);
    }
}
