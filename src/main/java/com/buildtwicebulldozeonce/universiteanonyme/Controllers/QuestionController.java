package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CommentDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.CommentPostDTO;
import com.buildtwicebulldozeonce.universiteanonyme.DTOs.QuestionFatDTO;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.Functions;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Comment;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CommentRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
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
    public List<CommentDTO> getCommentsForQuestion(@PathVariable("id") int id)
    {
        return QuestionService.getCommentsForQuestion(id).stream()
                .map(Comment::convertToDTO).sorted(Comparator.comparing(CommentDTO::getTimestamp)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/questions/{id}/ratings/add/{value}")
    public void addRating(@PathVariable("id") int questionId, @PathVariable("value") int value, @RequestBody HttpHeaders httpHeader)
    {
        String token = Functions.getValueFromHttpHeader(httpHeader, "token");
        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }
        QuestionService.addRating(token, questionId, value);
    }

    @RequestMapping(value = "/questions/{id}/comments/add", method = RequestMethod.POST)
    public void addComment(@PathVariable("id") int questionId, @RequestBody CommentPostDTO commentPostDTO, @RequestHeader HttpHeaders httpHeader)
    {
        String token = Functions.getValueFromHttpHeader(httpHeader, "token");

        if (!LoggedInUserService.isUserLoggedIn(token)) {
            log.info("User was not logged in");
            return;
        }

        Comment comment = Comment.builder()
                .message(commentPostDTO.getMessage())
                .timestamp(LocalDateTime.now())
                .type(Comment.CommentType.QuestionComment)
                .refID(questionId)
                .build();

        if (commentPostDTO.isAnon())
            comment.setAnonUser(LoggedInUserService.getLoggedInUser(token).getAnonUser());
        else
            comment.setUser(LoggedInUserService.getLoggedInUser(token).getUser());

        CommentService.addComment(comment);
    }

}
