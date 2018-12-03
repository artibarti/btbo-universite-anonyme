package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CoursePulseDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CourseRatingDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.SessionSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.InviteCodeGenerator;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Log
public class CourseController
{
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService)
    {
        this.courseService = courseService;
        this.userService = userService;
    }

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET)
    public CourseFatDTO getCourse(@PathVariable("id") int id)
    {
        return courseService.getCourse(id).convertToFatDTO();
    }

    @RequestMapping(value = "/courses/add", method = RequestMethod.POST)
    public void addCourse(@RequestBody CourseFatDTO courseFatDTO, @RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        Course course = new Course();
        course.setName(courseFatDTO.getName());
        course.setDescription(courseFatDTO.getDescription());
        course.setOwner(userService.getLoggedInUser(token).getValue1());

        courseService.addCourse(course);
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
    public List<CoursePulseDTO> getPulseForCourse(@PathVariable("id") int id)
    {
        return courseService.getPulseForCourse(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions", method = RequestMethod.GET)
    public Set<SessionSlimDTO> getAllSessionsForCourse(@PathVariable("courseID") int courseID)
    {
        return courseService.getSessionsForCourse(courseID).stream()
                .map(Session::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/courses/{courseID}/invite_codes", method = RequestMethod.GET)
    public Set<InviteCode> getAllInviteCodesForCourse(@RequestHeader HttpHeaders headers, @PathVariable("courseID") int courseID)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        return courseService.getAllInviteCodesForCourse(courseID);
    }

    @RequestMapping(value = "/courses/{courseID}/invite_codes/generate", method = RequestMethod.GET)
    public InviteCode generateInviteCodeForCourse(@RequestHeader HttpHeaders headers, @PathVariable("courseID") int courseID)
    {
        HashMap<String, String> headerValues = Functions.getValuesFromHttpHeader(headers, "token", "validUntil", "maxCopy");

        InviteCode inviteCode = new InviteCode();
        inviteCode.setCode(InviteCodeGenerator.GenerateInviteCode());
        inviteCode.setCourse(this.courseService.getCourse(courseID));
        inviteCode.setMaxCopy(Integer.parseInt( headerValues.get("maxCopy") ));

        this.courseService.addInviteCodeForCourse(inviteCode);
        return inviteCode;
    }
}
