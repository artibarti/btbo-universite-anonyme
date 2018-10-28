package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.UserRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(@NonNull User user) {
        userRepository.save(user);
    }

    public Set<Admin> getAdminRolesForUser(int id)
    {
        Set<Admin> result = new HashSet<>();
        userRepository.getAdminRolesForUser(id).forEach(result::add);
        return result;
    }

    public Set<Comment> getAllCommentsFromUser(int id)
    {
        Set<Comment> result = new HashSet<>();
        userRepository.getAllCommentsFromUser(id).forEach(result::add);
        return result;
    }

    public Set<Course> getCoursesCreatedByUser(int id)
    {
        Set<Course> result = new HashSet<>();
        userRepository.getCoursesCreatedByUser(id).forEach(result::add);
        return result;
    }

    public Set<Question> getAllQuestionsFromUser(int id)
    {
        Set<Question> result = new HashSet<>();
        // int anonID = getAnonIDForUser(id);
        userRepository.getAllQuestionsFromUser(id).forEach(result::add);
        return result;
    }

    public Set<Rating> getAllRatingsFromUser(int id)
    {
        Set<Rating> result = new HashSet<>();
        userRepository.getAllRatingsFromUser(id).forEach(result::add);
        return result;
    }
}
