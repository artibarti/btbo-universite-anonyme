package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class SessionService {

    private static SessionRepository sessionRepository;
    private static RatingRepository ratingRepository;
    private static QuestionRepository questionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, RatingRepository ratingRepository,
                          QuestionRepository questionRepository) {
        SessionService.sessionRepository = sessionRepository;
        SessionService.ratingRepository = ratingRepository;
        SessionService.questionRepository = questionRepository;
    }

    public static Session getSession(int id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public static void createSession(Session session) {
        log.trace("Entering SessionService::createSession...");

        int numberOfSessions = sessionRepository.getSessionsForCourse(session.getCourse().getId()).size();
        session.setCounter(numberOfSessions + 1);
        log.trace(session.toString());
        sessionRepository.save(session);
        log.trace(String.format("Successfully saved session %s for course %s",
                session.getName(),
                session.getCourse().getName()));
    }

    public static void deleteSession(Session session) {

        log.info("Cleaning up dependencies for session: "+ session.getId());

        log.info("Deleting questions related to the session");
        questionRepository.getQuestionsBySession_Id(session.getId()).forEach(QuestionService::deleteQuestion);

        log.info("Deleting ratings related to the session");
        ratingRepository.getRatingByRefIDAndType(session.getId(), Rating.RatingType.SessionRating).forEach(RatingService::deleteRating);

        log.info("Deleting sessios...");
        sessionRepository.delete(session);
    }

    public static void updateSession(Session session) {
        if (sessionRepository.existsById(session.getId()))
            sessionRepository.save(session);

    }

    public static Set<Question> getQuestionsForSession(int id) {
        return questionRepository.getQuestionsForSession(id);
    }

    public static Set<Rating> getRatingsForSession(int id) {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.SessionRating);
    }

    public static void changeSessionStatus(Session session, boolean status) {
        session.setActive(status);
        sessionRepository.save(session);
    }
}
