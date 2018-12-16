package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRoomCommentDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRoomDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRoomRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CourseRoomService {

    private static CourseRoomRepository courseRoomRepository;
    private static CommentRepository commentRepository;

    @Autowired
    public CourseRoomService(CourseRoomRepository courseRoomRepository, CommentRepository commentRepository) {
        CourseRoomService.courseRoomRepository = courseRoomRepository;
        CourseRoomService.commentRepository = commentRepository;
    }

    public static CourseRoom getCourseRoom(int id) {
        return courseRoomRepository.findById(id).orElse(null);
    }

    public static void addCourseRoom(int refId,String courseRoomName) {

        courseRoomRepository.save(CourseRoom.builder().name(courseRoomName).course(CourseService.getCourse(refId)).build());
    }

    public static List<Comment> getCommentsForCourseRoom(int id) {
        return commentRepository.getCommentByTypeAndID(id, Comment.CommentType.CourseRoomComment);
    }

    public static void updateCourseRoom(CourseRoom courseRoom) {
        courseRoomRepository.save(courseRoom);
    }

    public static void deleteCourseRoom(CourseRoom courseRoom) {
        log.info(String.format("Deleting comments related to courseroom with id: %s...", courseRoom.getId()));
        commentRepository.getCommentByRefIDAndType(courseRoom.getId(), Comment.CommentType.CourseRoomComment).forEach(CommentService::deleteComment);

        log.info("Deleting courseroom...");
        courseRoomRepository.delete(courseRoom);
    }

    public static CourseRoomDTO convertToCurseRoomDTO(CourseRoom courseRoom)
    {
        return new CourseRoomDTO(courseRoom.getId(),courseRoom.getName());
    }


}
