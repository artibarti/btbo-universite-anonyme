package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Services.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class QuestionController
{
    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}", method = RequestMethod.GET)
    public Question getQuestion(@PathVariable("id") int id)
    {
        return QuestionService.getQuestion(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/add", method = RequestMethod.POST)
    public void addQuestionForSession(@PathVariable("sessionID") int sessionID, @RequestBody Question question)
    {
        QuestionService.addQuestionForSession(sessionID, question);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/delete", method = RequestMethod.DELETE)
    public void deleteQuestion(@PathVariable("id") int id)
    {
        QuestionService.deleteQuestion(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/update", method = RequestMethod.PUT)
    public void updateQuestion(@RequestBody Question question)
    {
        QuestionService.updateQuestion(question);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForQuestion(@PathVariable("id") int id)
    {
        return QuestionService.getRatingsForQuestion(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/comments", method = RequestMethod.GET)
    public Set<Comment> getCommentsForQuestion(@PathVariable("id") int id)
    {
        return QuestionService.getCommentsForQuestion(id);
    }
}
