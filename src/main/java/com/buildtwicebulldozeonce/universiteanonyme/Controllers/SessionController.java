package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.SessionSlimDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CourseService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.QuestionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.SessionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

        log.info("Trying to create session...");
        if (CourseService.isThereAnActiveSessionForCourse(course) && sessionDTO.isActive()) {
            log.error(String.format("Can't create active session if there is already an active one. Active sessionid: %s session name: %s...",
                    CourseService.getActiveSession(course).getId(),
                    CourseService.getActiveSession(course).getName()));
            return;
        }
        log.info("" + sessionDTO);

        Session session = Session.builder()
                .name(sessionDTO.getName())
                .course(course)
                .isActive(sessionDTO.isActive())
                .build();

        SessionService.createSession(session);
    }

    @RequestMapping(value = "/sessions/{id}/changestatus", method = RequestMethod.POST)
    public void changeSessionStatus(@PathVariable("id") int id, @RequestBody boolean status, @RequestHeader HttpHeaders headers) {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        Session session = SessionService.getSession(id);
        User user = UserService.getLoggedInUser(token).getValue1();

        if (session.getCourse().getOwner().getId() != user.getId()) {
            log.error(String.format("%s %s can't deactivate this session: %s. (S)he is not the owner of the course!",
                    user.getFirstName(),
                    user.getLastName(),
                    session.getName()));
            return;
        }

        SessionService.changeSessionStatus(session, Functions.getValueFromHttpHeader(headers, "status").equals("0"));
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

    @RequestMapping(value = "/sessions/{id}/questions", method = RequestMethod.GET)
    public List<QuestionFatDTO> getQuestionsForActiveSession(@PathVariable("id") int id, @RequestHeader HttpHeaders headers) {
        List<QuestionFatDTO> questionFatDTOS = new ArrayList<>();
        QuestionService.getQuestionsForSession(SessionService.getSession(id)).forEach(question -> questionFatDTOS.add(QuestionService.convertToFatDTO(question)));
        questionFatDTOS.sort(Comparator.comparing(QuestionFatDTO::getTimestamp));
        return questionFatDTOS;
    }
}
