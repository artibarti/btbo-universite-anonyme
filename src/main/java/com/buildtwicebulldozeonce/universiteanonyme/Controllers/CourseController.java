package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.*;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.InviteCodeGenerator;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.LoggedInUserService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.RatingService;
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
    public CourseFatDTO getCourse(@PathVariable("id") int id)
    {
        return CourseService.convertToFatDTO(CourseService.getCourse(id));
    }

    @RequestMapping(value = "/courses/add", method = RequestMethod.POST)
    public CourseSlimDTO addCourse(@RequestBody CourseFatDTO courseFatDTO, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return null;
        }

        Course course = new Course();
        course.setName(courseFatDTO.getName());
        course.setDescription(courseFatDTO.getDescription());
        course.setOwner(LoggedInUserService.getLoggedInUser(token).getUser());
        course.setHidden(courseFatDTO.isHidden());

        CourseService.addCourse(course);
        return course.convertToSlimDTO();
    }

    @RequestMapping(value = "/courses/{id}/update", method = RequestMethod.PUT)
    public void updateCourse(@PathVariable("id") int id, @RequestBody Course course, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");

        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }

        if (UserService.checkIfUserOwnsCourse(id, token)) {
            CourseService.updateCourse(course);
        }
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.DELETE)
    public void deleteCourse(@PathVariable("id") int id, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers,"token");

        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }

        LoggedInUser loggedInUser = LoggedInUserService.getLoggedInUser(token);

        Course course = CourseService.getCourse(id);

        if (course.getOwner().getId() == loggedInUser.getUser().getId()) {
            CourseService.deleteCourse(course);
        } else {
            log.info("Course not owned by the user");
        }

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

        String token = Functions.getValueFromHttpHeader(headers,"token");
        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }
        CourseService.leaveCourse(id, token);
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

    @RequestMapping(value = "/courses/{id}/ratings/remove",method = RequestMethod.GET)
    public void removeRating(@PathVariable("id") int courseId,@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers,"token");
        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }
        CourseService.removeRating(LoggedInUserService.getLoggedInUser(token).getAnonUser(),courseId, Rating.RatingType.CourseRating);
    }

    @RequestMapping(value = "/courses/{id}/ratings/add/{value}", method = RequestMethod.POST)
    public void addRating(@PathVariable("id") int courseId, @PathVariable("value") int value, @RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }
        CourseService.addRating(token, value, courseId);
    }

    @RequestMapping(value = "/courses/{id}/alreadyRated",method = RequestMethod.GET)
    public boolean isAlreadyRated(@PathVariable("id") int courseId,@RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        return RatingService.alreadyRated(LoggedInUserService.getLoggedInUser(token).getAnonUser(), Rating.RatingType.CourseRating,courseId);
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
    public Set<CourseFatDTO> getHotCourses(@RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return null;
        }
        return CourseService.getHotCourses(token).stream()
                .map(CourseService::convertToFatDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/courses/{id}/activesession", method = RequestMethod.GET)
    public SessionSlimDTO getActiveSession(@PathVariable("id") int id, @RequestHeader HttpHeaders headers)
    {
        return CourseService.getActiveSession(CourseService.getCourse(id));
    }

    @RequestMapping(value = "/courses/{id}/owner/isme", method = RequestMethod.GET)
    public Integer amITheOwner(@PathVariable("id") int id, @RequestHeader HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return null;
        }
        if (UserService.checkIfUserOwnsCourse(id, token))
            return 1;
        else
            return 0;
    }
}
