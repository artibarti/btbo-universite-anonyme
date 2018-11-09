package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.AnonUserRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.CourseRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AnonUserService {

    private final AnonUserRepository anonUserRepository;

    @Autowired
    public AnonUserService(AnonUserRepository anonUserRepository) {
        this.anonUserRepository = anonUserRepository;
    }

    public List<AnonUser> getAllAnonUsers() {
        return Lists.newArrayList(anonUserRepository.findAll());
    }

    public AnonUser getAnonUser(int id) {
        return anonUserRepository.findById(id).orElse(null);
    }

    public void addAnonUser(@NonNull AnonUser anonUser) {
        anonUserRepository.save(anonUser);
    }

    public Set<Question> getAllQuestionsFromAnonUser(int id)
    {
        return anonUserRepository.getAllQuestionsFromAnonUser(id);
    }

    public Set<Comment> getAllCommentsFromUser(int id)
    {
        return anonUserRepository.getAllCommentsFromAnonUser(id);
    }

    public Set<CourseSubs> getCourseSubsForAnonUser(int id)
    {
        return anonUserRepository.getCourseSubsForAnonUser(id);
    }
}
