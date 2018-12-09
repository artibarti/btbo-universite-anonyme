package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.SessionSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.SessionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class SessionController {
    @RequestMapping(value = "/courses/{courseID}/sessions/{id}", method = RequestMethod.GET)
    public Session getSession(@PathVariable("courseID") int courseID, @PathVariable("id") int id) {
        return SessionService.getSession(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}/questions", method = RequestMethod.GET)
    public Set<QuestionSlimDTO> getQuestionsForSession(@PathVariable("courseID") int courseID, @PathVariable("id") int id) {
        return SessionService.getQuestionsForSession(id).stream()
                .map(Question::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/sessions/create", method = RequestMethod.POST)
    public void createSession(@RequestBody SessionSlimDTO sessionDTO, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        Course course = CourseService.getCourse(sessionDTO.getCourseId());
        User user = UserService.getLoggedInUser(token).getValue1();

        if (course.getOwner().getId() != user.getId()) {
            log.error(String.format("%s %s can't create session for %s. (S)he is not the owner of the course!",
                    user.getFirstName(),
                    user.getLastName(),
                    course.getName()));
            return;
        }

        Session session = Session.builder()
                .name(sessionDTO.getName())
                .course(course)
                .build();

        SessionService.createSession(session);
    }

    @RequestMapping(value = "/sessions/{id}/activate", method = RequestMethod.POST)
    public void changeSessionStatus(@PathVariable("id") int id, @RequestBody boolean status, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        User user = UserService.getLoggedInUser(token).getValue1();

        SessionService.changeSessionStatus(SessionService.getSession(id), Functions.getValueFromHttpHeader(headers, "status").equals("0"));

    }

    @RequestMapping(value = "/sessions/delete", method = RequestMethod.DELETE)
    public void deleteSession(@RequestBody SessionSlimDTO sessionDTO, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        Course course = CourseService.getCourse(sessionDTO.getCourseId());
        User user = UserService.getLoggedInUser(token).getValue1();

        if (course.getOwner().getId() != user.getId()) {
            log.error(String.format("%s %s can't delete this session: %s. (S)he is not the owner of the course!",
                    user.getFirstName(),
                    user.getLastName(),
                    course.getName()));
            return;
        }

        SessionService.deleteSession(SessionService.getSession(sessionDTO.getId()));
    }

    @RequestMapping(value = "/courses/{courseiID}/sessions/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForSession(@PathVariable("id") int id) {
        return SessionService.getRatingsForSession(id);
    }
}
