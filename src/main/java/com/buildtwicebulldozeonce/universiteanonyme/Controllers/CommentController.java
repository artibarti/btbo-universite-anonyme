package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class CommentController
{
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/{id}", method = RequestMethod.GET)
    public Comment getComment(@PathVariable("id") int id)
    {
        return commentService.getComment(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/add", method = RequestMethod.POST)
    public void addComment(@RequestBody Comment comment)
    {
        commentService.addComment(comment);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/{id}/delete", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("id") int id)
    {
        commentService.deleteComment(id);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/{id}/update", method = RequestMethod.PUT)
    public void updateComment(@RequestBody Comment comment)
    {
        commentService.updateComment(comment);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForComment(@PathVariable("id") int id)
    {
        return commentService.getRatingsForComment(id);
    }

}
