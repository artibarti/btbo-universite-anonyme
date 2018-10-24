package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Session;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.SessionRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getAllSessions() {
        return Lists.newArrayList(sessionRepository.findAll());
    }

    public Session getSession(int id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public void addSession(@NonNull Session session) {
        sessionRepository.save(session);
    }
}
