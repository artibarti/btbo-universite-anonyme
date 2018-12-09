package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Course;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.SessionRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SessionService {

    private static SessionRepository sessionRepository;
    private static RatingRepository ratingRepository;
    private static QuestionRepository questionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, RatingRepository ratingRepository,
                          QuestionRepository questionRepository)
    {
        SessionService.sessionRepository = sessionRepository;
        SessionService.ratingRepository = ratingRepository;
        SessionService.questionRepository = questionRepository;
    }

    public static Session getSession(int id)
    {
        return sessionRepository.findById(id).orElse(null);
    }

    public static void createSession(Session session)
    {
        log.trace("Entering SessionService::createSession...");
        int numberOfSessions = sessionRepository.getSessionsForCourse(session.getCourse().getId()).size();
        session.setCounter(numberOfSessions + 1);
        log.trace(session.toString());
        sessionRepository.save(session);
        log.trace(String.format("Successfully saved session %s for course %s",
                session.getName(),
                session.getCourse().getName()));
    }

    public static void deleteSessionsByCourse(Course course)
    {
        CourseService.getSessionsForCourse(course.getId()).forEach(SessionService::deleteSession);
        log.info("Successfully deleted every session for course => " + course.getName());
    }

    public static void deleteSession(Session session)
    {
        QuestionService.deleteQuestionsBySession(session);
        sessionRepository.delete(session);
        log.info("Successfully deleted session => " + session.getName());
    }

    public static void changeSessionStatus(Session session, boolean status) {
        session.setActive(status);
    }

    public static Set<Question> getQuestionsForSession(int id)
    {
        return questionRepository.getQuestionsForSession(id);
    }

    public static Set<Rating> getRatingsForSession(int id)
    {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.SessionRating);
    }

}
