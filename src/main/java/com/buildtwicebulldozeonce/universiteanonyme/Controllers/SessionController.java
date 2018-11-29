package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Services.SessionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SessionController
{
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}", method = RequestMethod.GET)
    public Session getSession(@PathVariable("courseID") int courseID, @PathVariable("id") int id)
    {
        return sessionService.getSession(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions", method = RequestMethod.GET)
    public Set<Session> getAllSessionsForCourse(@PathVariable("courseID") int courseID)
    {
        return sessionService.getAllSessionsForCourse(courseID);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}/questions", method = RequestMethod.GET)
    public Set<Question> getQuestionsForSession(@PathVariable("courseID") int courseID, @PathVariable("id") int id)
    {
        return sessionService.getQuestionsForSession(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/", method = RequestMethod.POST)
    public void addSession(@PathVariable("courseID") int courseID, @RequestBody Session session)
    {
        sessionService.addSession(courseID, session);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}/delete", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable("id") int id)
    {
        sessionService.deleteSession(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}/update", method = RequestMethod.PUT)
    public void updateSession(@RequestBody Session session)
    {
        sessionService.updateSession(session);
    }

    @RequestMapping(value = "/courses/{courseiID}/sessions/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForSession(@PathVariable("id") int id)
    {
        return sessionService.getRatingsForSession(id);
    }

}
