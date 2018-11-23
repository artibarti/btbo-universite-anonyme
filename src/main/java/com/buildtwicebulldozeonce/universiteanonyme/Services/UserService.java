package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.TokenHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.AnonUserRepository;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.UserRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Log
@Service
public class UserService {

    private List<Triplet<String, User, AnonUser>> loggedInUsers = new ArrayList<Triplet<String, User, AnonUser>>();

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
        log.info("authenticate user with email: " + anonName + " and password: " + hashedPassword);

        AnonUser anonUser = anonUserRepository.findByAnonNameAndHashedPassword(anonName,hashedPassword);

        if(anonUser == null) return null;

        String doubleHashedPassword = PasswordHelper.hashString(hashedPassword);
        User user = userRepository.findByDoubleHashedPassword(doubleHashedPassword);

        if(user == null) return null;

        String token = TokenHelper.generateToken();

        loggedInUsers.add(new Triplet<String,User,AnonUser>(token,user,anonUser));

        System.out.println("trying to authenticate user with username: " + anonName + " and password: " + doubleHashedPassword);
        return user;
    }
}
