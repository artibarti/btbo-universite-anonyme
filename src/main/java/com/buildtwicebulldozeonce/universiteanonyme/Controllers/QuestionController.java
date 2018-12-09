package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Services.QuestionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.SessionService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class QuestionController {
    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}", method = RequestMethod.GET)
    public Question getQuestion(@PathVariable("id") int id) {
        return QuestionService.getQuestion(id);
    }

    @RequestMapping(value = "/sessions/{sessionID}/questions/add", method = RequestMethod.POST)
    public void addQuestionForSession(@PathVariable("sessionID") int sessionID, @RequestBody String question, @RequestHeader HttpHeaders headers) {
        QuestionService.addQuestionForSession(SessionService.getSession(sessionID), question, Functions.getValueFromHttpHeader(headers, "token"));
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/delete", method = RequestMethod.DELETE)
    public void deleteQuestion(@PathVariable("id") int id) {
        QuestionService.deleteQuestion(QuestionService.getQuestion(id));
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/update", method = RequestMethod.PUT)
    public void updateQuestion(@RequestBody Question question) {
        QuestionService.updateQuestion(question);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForQuestion(@PathVariable("id") int id) {
        return QuestionService.getRatingsForQuestion(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/comments", method = RequestMethod.GET)
    public Set<Comment> getCommentsForQuestion(@PathVariable("id") int id) {
        return QuestionService.getCommentsForQuestion(id);
    }

    @RequestMapping(value = "/questions/{id}/ratings/add/{value}")
    public void addRating(@PathVariable("id") int questionId, @PathVariable("value") int value, @RequestBody HttpHeaders httpHeader)
    {
        String token = Functions.getValueFromHttpHeader(httpHeader, "token");
        QuestionService.addRating(token, questionId, value);
    }
}
