package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CommentDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CommentPostDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Services.CommentService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.QuestionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.SessionService;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/questions/{id}/delete", method = RequestMethod.DELETE)
    public void deleteQuestion(@PathVariable("id") int id) {
        QuestionService.deleteQuestion(QuestionService.getQuestion(id));
    }

    @RequestMapping(value = "/questions/{id}/update", method = RequestMethod.PUT)
    public void updateQuestion(@RequestBody Question question) {
        QuestionService.updateQuestion(question);
    }

    @RequestMapping(value = "/questions/{id}/ratings", method = RequestMethod.GET)
    public Set<Rating> getRatingsForQuestion(@PathVariable("id") int id) {
        return QuestionService.getRatingsForQuestion(id);
    }

    @RequestMapping(value = "/questions/{id}/comments", method = RequestMethod.GET)
    public Set<CommentDTO> getCommentsForQuestion(@PathVariable("id") int id)
    {
        return QuestionService.getCommentsForQuestion(id).stream()
                .map(Comment::convertToDTO)
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/questions/{id}/ratings/add/{value}")
    public void addRating(@PathVariable("id") int questionId, @PathVariable("value") int value, @RequestBody HttpHeaders httpHeader)
    {
        String token = Functions.getValueFromHttpHeader(httpHeader, "token");
        QuestionService.addRating(token, questionId, value);
    }

    @RequestMapping(value = "/questions/{id}/comments/add", method = RequestMethod.POST)
    public void addComment(@PathVariable("id") int questionId, @RequestBody CommentPostDTO commentPostDTO, @RequestHeader HttpHeaders httpHeader)
    {
        String token = Functions.getValueFromHttpHeader(httpHeader, "token");

        Comment comment = Comment.builder()
                .message(commentPostDTO.getMessage())
                .timestamp(LocalDateTime.now())
                .type(Comment.CommentType.QuestionComment)
                .refID(questionId)
                .build();

        if (commentPostDTO.isAnon())
            comment.setAnonUser(UserService.getLoggedInUser(token).getValue2());
        else
            comment.setUser(UserService.getLoggedInUser(token).getValue1());

        CommentService.addComment(comment);
    }

}
