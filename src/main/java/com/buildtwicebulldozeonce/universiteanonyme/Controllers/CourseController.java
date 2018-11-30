package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRatingDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@Log
public class CourseController
{
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService)
    {
        this.courseService = courseService;
    }

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET)
    public CourseFatDTO getCourse(@PathVariable("id") int id)
    {
        return courseService.getCourse(id).convertToFatDTO();
    }

    @RequestMapping(value = "/courses/add", method = RequestMethod.POST)
    public void addCourse(@RequestBody CourseFatDTO course)
    {
        log.info("/courses/add endpoint reached");
        log.info("Reading request body...");
        log.info("course.name: " + course.getName());
        log.info("course.description" + course.getDescription());
        // courseService.addCourse(course);
    }

    @RequestMapping(value = "/courses/{id}/update", method = RequestMethod.PUT)
    public void updateCourse(@RequestBody Course course)
    {
        courseService.updateCourse(course);
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.DELETE)
    public void deleteCourse(@PathVariable("id") int id)
    {
        courseService.deleteCourse(id);
    }

    @RequestMapping(value = "/courses/{id}/admins", method = RequestMethod.GET)
    public Set<User> getCourseAdminsForCourse(@PathVariable("id") int id)
    {
        return courseService.getCourseAdmins(id);
    }

    @RequestMapping(value = "/courses/{id}/subs", method = RequestMethod.GET)
    public Set<CourseSubs> getCourseSubsForCourse(@PathVariable("id") int id)
    {
        return courseService.getCourseSubs(id);
    }

    @RequestMapping(value = "/courses/{id}/subs/sum", method = RequestMethod.GET)
    public Integer getCourseSubsSumForCourse(@PathVariable("id") int id)
    {
        return courseService.getCourseSubs(id).size();
    }

    @RequestMapping(value = "/courses/{id}/rooms", method = RequestMethod.GET)
    public Set<CourseRoom> getCourseRoomsForCourse(@PathVariable("id") int id)
    {
        return courseService.getCourseRooms(id);
    }

    @RequestMapping(value = "/courses/{id}/sessions", method = RequestMethod.GET)
    public Set<Session> getSessionsForCourse(@PathVariable("id") int id)
    {
        return courseService.getSessionsForCourse(id);
    }

    @RequestMapping(value = "/courses/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForCourse(@PathVariable("id") int id)
    {
        return courseService.getRatingsForCourse(id);
    }

    @RequestMapping(value = "/courses/{id}/ratings/sum", method = RequestMethod.GET)
    public CourseRatingDTO getRatingSumForCourse(@PathVariable("id") int id)
    {
        return courseService.getRatingSumForCourse(id);
    }

    @RequestMapping(value = "/courses/{id}/pulse", method = RequestMethod.GET)
    public List<Integer> getPulseForCourse(@PathVariable("id") int id)
    {
        return courseService.getPulseForCourse(id);
    }
}
