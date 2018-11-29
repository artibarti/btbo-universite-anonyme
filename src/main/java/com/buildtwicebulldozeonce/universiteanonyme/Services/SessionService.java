package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Question;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Rating;
import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.QuestionRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.RatingRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.SessionRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final RatingRepository ratingRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, RatingRepository ratingRepository,
                          QuestionRepository questionRepository)
    {
        this.sessionRepository = sessionRepository;
        this.ratingRepository = ratingRepository;
        this.questionRepository = questionRepository;
    }

    public Set<Session> getAllSessionsForCourse(int id)
    {
        return sessionRepository.getSessionsForCourse(id);
    }

    public Session getSession(int id)
    {
        return sessionRepository.findById(id).orElse(null);
    }

    public void addSession(int courseID, @NonNull Session session)
    {

    }

    public void deleteSession(int id)
    {
        sessionRepository.deleteById(id);
    }

    public void updateSession(Session session)
    {
        if (sessionRepository.existsById(session.getId()))
            sessionRepository.save(session);
    }

    public Set<Question> getQuestionsForSession(int id)
    {
        return questionRepository.getQuestionsForSession(id);
    }

    public Set<Rating> getRatingsForSession(int id)
    {
        return ratingRepository.getRatingsByTypeAndID(id, Rating.RatingType.SessionRating);
    }

}
