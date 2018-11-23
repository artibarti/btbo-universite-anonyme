package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.TokenHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.AnonUserRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.UserRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.util.*;

@Log
@Service
public class UserService {

    private List<Tuple3<String, User, AnonUser>> loggedInUsers = new ArrayList<>();

    private final UserRepository userRepository;
    private final AnonUserRepository anonUserRepository;

    @Autowired
    public UserService(UserRepository userRepository, AnonUserRepository anonUserRepository) {
        this.userRepository = userRepository;
        this.anonUserRepository = anonUserRepository;
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

    public boolean checkIfUserExists(String email)
    {
        return userRepository.findByEmail(email) != null;
    }

    public User authenticateUser(String anonName, String hashedPassword)
    {
        log.info("authenticate user with email: " + email + " and password: " + doubleHashedPassword);

        AnonUser anonUser = anonUserRepository.findByAnonNameAndHashedPassword(anonName,hashedPassword);

        if(anonUser == null) return null;

        String doubleHashedPassword = PasswordHelper.hashString(hashedPassword);
        User user = userRepository.findByDoubleHashedPassword(doubleHashedPassword);

        if(user == null) return null;

        String token = TokenHelper.generateToken();

        loggedInUsers.add(new Tuple3<>(token,user,anonUser));

        System.out.println("trying to authenticate user with username: " + email + " and password: " + doubleHashedPassword);
        return user;
    }
}
