package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.*;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.InviteCodeGenerator;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class CourseController {

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET)
    public CourseFatDTO getCourse(@PathVariable("id") int id) {
        return CourseService.getCourse(id).convertToFatDTO();
    }

    @RequestMapping(value = "/courses/add", method = RequestMethod.POST)
    public CourseSlimDTO addCourse(@RequestBody CourseFatDTO courseFatDTO, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        Course course = new Course();
        course.setName(courseFatDTO.getName());
        course.setDescription(courseFatDTO.getDescription());
        course.setOwner(UserService.getLoggedInUser(token).getValue1());
        course.setHidden(courseFatDTO.isHidden());

        CourseService.addCourse(course);
        return course.convertToSlimDTO();
    }

    @RequestMapping(value = "/courses/{id}/update", method = RequestMethod.PUT)
    public void updateCourse(@PathVariable("id") int id, @RequestBody Course course, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        if (UserService.checkIfUserOwnsCourse(id, token)) {
            CourseService.updateCourse(course);
        }
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.DELETE)
    public void deleteCourse(@PathVariable("id") int id, @RequestHeader HttpHeaders headers) {
        Course course = CourseService.getCourse(id);
        CourseService.deleteCourse(course);
    }

    @RequestMapping(value = "/courses/{id}/admins", method = RequestMethod.GET)
    public Set<User> getCourseAdminsForCourse(@PathVariable("id") int id) {
        return CourseService.getCourseAdmins(id);
    }

    @RequestMapping(value = "/courses/{id}/subs", method = RequestMethod.GET)
    public Set<AnonUser> getCourseSubsForCourse(@PathVariable("id") int id) {
        return CourseService.getCourseSubs(id);
    }

    @RequestMapping(value = "/courses/{id}/leave", method = RequestMethod.GET)
    public void leaveCourse(@PathVariable("id") int id, @RequestHeader HttpHeaders headers) {
        CourseService.leaveCourse(id, Functions.getValueFromHttpHeader(headers, "token"));
    }

    @RequestMapping(value = "/courses/{id}/subs/sum", method = RequestMethod.GET)
    public Integer getCourseSubsSumForCourse(@PathVariable("id") int id) {
        Set<AnonUser> result = CourseService.getCourseSubs(id);
        if (result != null)
            return result.size();
        else
            return 0;
    }

    @RequestMapping(value = "/courses/{id}/rooms", method = RequestMethod.GET)
    public Set<CourseRoom> getCourseRoomsForCourse(@PathVariable("id") int id) {
        return CourseService.getCourseRooms(id);
    }

    @RequestMapping(value = "/courses/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForCourse(@PathVariable("id") int id) {
        return CourseService.getRatingsForCourse(id);
    }

    @RequestMapping(value = "/courses/{id}/ratings/sum", method = RequestMethod.GET)
    public CourseRatingDTO getRatingSumForCourse(@PathVariable("id") int id) {
        return CourseService.getRatingSumForCourse(id);
    }

    @RequestMapping(value = "/courses/{id}/ratings/add/{value}", method = RequestMethod.POST)
    public void addRating(@PathVariable("id") int courseId, @PathVariable("value") int value, @RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        CourseService.addRating(token, value, courseId);
    }

    @RequestMapping(value = "/courses/{id}/pulse", method = RequestMethod.GET)
    public List<CoursePulseDTO> getPulseForCourse(@PathVariable("id") int id) {
        return CourseService.getPulseForCourse(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions", method = RequestMethod.GET)
    public Set<SessionSlimDTO> getAllSessionsForCourse(@PathVariable("courseID") int courseID) {
        return CourseService.getSessionsForCourse(courseID).stream()
                .map(Session::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/courses/{courseID}/invitecodes", method = RequestMethod.GET)
    public Set<InviteCode> getAllInviteCodesForCourse(@RequestHeader HttpHeaders headers, @PathVariable("courseID") int courseID) {
        return CourseService.getAllInviteCodesForCourse(courseID, Functions.getValueFromHttpHeader(headers, "token"));
    }

    @RequestMapping(value = "/courses/{courseID}/invitecodes/generate", method = RequestMethod.POST)
    public InviteCode generateInviteCodeForCourse(@RequestBody InviteCodeDTO inviteCodeDTO, @RequestHeader HttpHeaders headers, @PathVariable("courseID") int courseID) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        if (UserService.checkIfUserOwnsCourse(courseID, token)) {
            Course course = CourseService.getCourse(courseID);
            if (course == null) {
                return null;
            }

            InviteCode inviteCode = new InviteCode();
            inviteCode.setMaxCopy(inviteCodeDTO.getMaxCopy());
            inviteCode.setValidUntil(inviteCodeDTO.getValidUntil());
            inviteCode.setCode(InviteCodeGenerator.GenerateInviteCode());
            inviteCode.setCourse(course);

            CourseService.addInviteCodeForCourse(inviteCode);
            return inviteCode;
        }

        return null;
    }

    @RequestMapping(value = "/courses/hot", method = RequestMethod.GET)
    public Set<CourseFatDTO> getHotCourses(@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        int id = UserService.getLoggedInUser(token).getValue2().getId();
        return CourseService.getHotCourses(id).stream()
                .map(Course::convertToFatDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/courses/{id}/activesession", method = RequestMethod.GET)
    public SessionSlimDTO getActiveSession(@PathVariable("id") int id, @RequestHeader HttpHeaders headers) {
        return CourseService.getActiveSession(CourseService.getCourse(id));
    }
}
