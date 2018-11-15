package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.CourseRoom;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class CourseRoomController
{
    private final CourseRoomService courseRoomService;

    @Autowired
    public CourseRoomController(CourseRoomService courseRoomService) {
        this.courseRoomService = courseRoomService;
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms/{id}", method = RequestMethod.GET)
    public CourseRoom getCourseRoom(@PathVariable("id") int id)
    {
        return courseRoomService.getCourseRoom(id);
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms", method = RequestMethod.POST)
    public void addCourseRoom(@RequestBody CourseRoom courseRoom)
    {
        courseRoomService.addCourseRoom(courseRoom);
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms/{id}", method = RequestMethod.DELETE)
    public void deleteCourseRoom(@PathVariable("id") int id)
    {
        courseRoomService.deleteCourseRoom(id);
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms/{id}", method = RequestMethod.PUT)
    public void updateCourseRoom(@RequestBody CourseRoom courseRoom)
    {
        courseRoomService.updateCourseRoom(courseRoom);
    }

    @RequestMapping(value = "/courses/{courseID}/courserooms/{id}/comments", method = RequestMethod.GET)
    public Set<Comment> getComments(@PathVariable("id") int id)
    {
        return courseRoomService.getComments(id);
    }

}