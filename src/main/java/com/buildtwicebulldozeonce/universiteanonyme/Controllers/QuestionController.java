package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import com.buildtwicebulldozeonce.universiteanonyme.Services.QuestionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class QuestionController
{
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}", method = RequestMethod.GET)
    public Question getQuestion(@PathVariable("id") int id)
    {
        return questionService.getQuestion(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}", method = RequestMethod.DELETE)
    public void deleteQuestion(@PathVariable("id") int id)
    {
        questionService.deleteQuestion(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}", method = RequestMethod.PUT)
    public void updateQuestion(@RequestBody Question question)
    {
        questionService.updateQuestion(question);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/", method = RequestMethod.POST)
    public void addQuestion(@RequestBody Question question)
    {
        questionService.addQuestion(question);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForQuestion(@PathVariable("id") int id)
    {
        return questionService.getRatings(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{id}/comments", method = RequestMethod.GET)
    public Set<Comment> getCommentsForQuestion(@PathVariable("id") int id)
    {
        return questionService.getComments(id);
    }
}
