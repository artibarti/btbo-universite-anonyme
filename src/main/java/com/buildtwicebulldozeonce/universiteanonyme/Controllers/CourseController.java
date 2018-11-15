package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CourseController
{
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public List<CourseDTO> getAllCourses()
    {
        return courseService.getAllCourses()
                .stream()
                .map(Course::convertToDTO)
                .collect(Collectors.toList()) ;
    }

    @RequestMapping(value = "/courses", method = RequestMethod.POST)
    public void addCourse(@RequestBody Course course)
    {
        courseService.addCourse(course);
    }

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.PUT)
    public void updateCourse(@RequestBody Course course)
    {
        courseService.updateCourse(course);
    }

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE)
    public void deleteCourse(@PathVariable("id") int id)
    {
        courseService.deleteCourse(id);
    }

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET)
    public Course getCourse(@PathVariable("id") int id)
    {
        return courseService.getCourse(id);
    }

    @RequestMapping(value = "/courses/{id}/admins", method = RequestMethod.GET)
    public Set<Admin> getCourseAdmins(@PathVariable("id") int id)
    {
        return courseService.getCourseAdmins(id);
    }

    @RequestMapping(value = "/courses/{id}/subs", method = RequestMethod.GET)
    public Set<CourseSubs> getCourseSubs(@PathVariable("id") int id)
    {
        return courseService.getCourseSubs(id);
    }

    @RequestMapping(value = "/courses/{id}/rooms", method = RequestMethod.GET)
    public Set<CourseRoom> getCourseRooms(@PathVariable("id") int id)
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
}
