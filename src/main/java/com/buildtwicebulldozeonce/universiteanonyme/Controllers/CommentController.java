package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CommentService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class CommentController {
    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/{id}", method = RequestMethod.GET)
    public Comment getComment(@PathVariable("id") int id) {
        return CommentService.getComment(id);
    }

    @RequestMapping(value = "/comments/{id}/delete", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable("id") int id) {
        CommentService.deleteComment(CommentService.getComment(id));
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/{id}/update", method = RequestMethod.PUT)
    public void updateComment(@RequestBody Comment comment) {
        CommentService.updateComment(comment);
    }

    @RequestMapping(value = "comments/{id}/ratings/add/{value}")
    public void addRating(@PathVariable("id") int commentId, @PathVariable("value") int value, @RequestBody HttpHeaders headers)
    {
        String token = Functions.getValueFromHttpHeader(headers, "token");
        CommentService.addRating(token, value, commentId);
    }

    @RequestMapping(value = "/courses/{courseID}/sessions/{sessionID}/questions/{questionID}/comments/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForComment(@PathVariable("id") int id) {
        return CommentService.getRatingsForComment(id);
    }
}
