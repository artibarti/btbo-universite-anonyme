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
        return userRepository.getAdminRolesForUser(id);
    }

    public Set<Comment> getAllCommentsFromUser(int id)
    {
        return userRepository.getAllCommentsFromUser(id);
    }

    public Set<Course> getCoursesCreatedByUser(int id)
    {
        return userRepository.getCoursesCreatedByUser(id);
    }

    public Set<Question> getAllQuestionsFromUser(int id)
    {
        // int anonID = getAnonIDForUser(id);
        return userRepository.getAllQuestionsFromUser(id);
    }

    public Set<Rating> getAllRatingsFromUser(int id)
    {
        return userRepository.getAllRatingsFromUser(id);
    }

    public void deleteUser(int id)
    {
        userRepository.deleteById(id);
    }

    public void updateUser(User user)
    {
        if (userRepository.existsById(user.getId()))
            userRepository.save(user);
    }
}
