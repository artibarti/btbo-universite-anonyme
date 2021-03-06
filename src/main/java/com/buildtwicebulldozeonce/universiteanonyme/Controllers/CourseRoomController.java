package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CommentPostDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRoomCommentDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CommentService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseRoomService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.LoggedInUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class CourseRoomController {
    @RequestMapping(value = "/courses/{courseID}/courserooms/{id}", method = RequestMethod.GET)
    public CourseRoom getCourseRoom(@PathVariable("id") int id) {
        return CourseRoomService.getCourseRoom(id);
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms/add", method = RequestMethod.POST)
    public void addCourseRoom(@PathVariable("courseID") int courseID,@RequestBody String courseRoomName) {
        CourseRoomService.addCourseRoom(courseID,courseRoomName);
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms/{id}/delete", method = RequestMethod.DELETE)
    public void deleteCourseRoom(@PathVariable("id") int id) {
        CourseRoomService.deleteCourseRoom(CourseRoomService.getCourseRoom(id));
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms/{id}/put", method = RequestMethod.PUT)
    public void updateCourseRoom(@RequestBody CourseRoom courseRoom) {
        CourseRoomService.updateCourseRoom(courseRoom);
    }

    @RequestMapping(value = "/courserooms/{id}/comments", method = RequestMethod.GET)
    public List<CourseRoomCommentDTO> getCommentsForCourseRoom(@PathVariable("id") int id, @RequestHeader HttpHeaders headers) {
        List<Comment> temp = CourseRoomService.getCommentsForCourseRoom(id);
        String token = Functions.getValueFromHttpHeader(headers,"token");

        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return null;
        }


        List<CourseRoomCommentDTO> result = new ArrayList<>();

        for (Comment comment:temp) {
            result.add(CommentService.convertToCourseRoomCommentDTO(comment,token));
        }

        result.sort(Comparator.comparing(CourseRoomCommentDTO::getTimeStamp));

        return result;
    }

    @RequestMapping(value = "/courserooms/{id}/comments/add", method = RequestMethod.POST)
    public void addComment(@PathVariable("id") int courseroomid, @RequestBody CommentPostDTO commentPostDTO, @RequestHeader HttpHeaders httpHeader)
    {
        String token = Functions.getValueFromHttpHeader(httpHeader, "token");

        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }
        Comment comment = Comment.builder()
                .message(commentPostDTO.getMessage())
                .timestamp(LocalDateTime.now())
                .type(Comment.CommentType.CourseRoomComment)
                .refID(courseroomid)
                .build();

        if (commentPostDTO.isAnon())
            comment.setAnonUser(LoggedInUserService.getLoggedInUser(token).getAnonUser());
        else
            comment.setUser(LoggedInUserService.getLoggedInUser(token).getUser());

        CommentService.addComment(comment);
    }
}
