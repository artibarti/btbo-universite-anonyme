package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionSlimDTO;
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
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class SessionController
{
    @RequestMapping(value = "/courses/{courseID}/sessions/{id}", method = RequestMethod.GET)
    public Session getSession(@PathVariable("courseID") int courseID, @PathVariable("id") int id)
    {
        return SessionService.getSession(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}/questions", method = RequestMethod.GET)
    public Set<QuestionSlimDTO> getQuestionsForSession(@PathVariable("courseID") int courseID, @PathVariable("id") int id)
    {
        return SessionService.getQuestionsForSession(id).stream()
                .map(Question::convertToSlimDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/", method = RequestMethod.POST)
    public void addSession(@PathVariable("courseID") int courseID, @RequestBody Session session)
    {
        SessionService.addSession(courseID, session);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}/delete", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable("id") int id)
    {
        SessionService.deleteSession(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{id}/update", method = RequestMethod.PUT)
    public void updateSession(@RequestBody Session session)
    {
        SessionService.updateSession(session);
    }

    @RequestMapping(value = "/courses/{courseiID}/sessions/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForSession(@PathVariable("id") int id)
    {
        return SessionService.getRatingsForSession(id);
    }

}
