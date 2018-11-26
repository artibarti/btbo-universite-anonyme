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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AnonUserRepository anonUserRepository;
    private List<Triplet<String, User, AnonUser>> loggedInUsers = new ArrayList<Triplet<String, User, AnonUser>>();

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

    public Set<Admin> getAdminRolesForUser(int id) {
        return userRepository.getAdminRolesForUser(id);
    }

    public Set<Comment> getAllCommentsFromUser(int id) {
        return userRepository.getAllCommentsFromUser(id);
    }

    public Set<Course> getCoursesCreatedByUser(int id) {
        return userRepository.getCoursesCreatedByUser(id);
    }

    public Set<Question> getAllQuestionsFromUser(int id) {
        // int anonID = getAnonIDForUser(id);
        return userRepository.getAllQuestionsFromUser(id);
    }

    public Set<Rating> getAllRatingsFromUser(int id) {
        return userRepository.getAllRatingsFromUser(id);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        if (userRepository.existsById(user.getId()))
            userRepository.save(user);
    }

    public boolean checkIfUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User authenticateUser(String anonName, String hashedPassword) {
        log.info("authenticate user with email: " + anonName + " and password: " + hashedPassword);

        AnonUser anonUser = anonUserRepository.findByAnonName(anonName);


        if (anonUser == null || !PasswordHelper.comparePassword(anonUser.getHashedPassword(), hashedPassword)) {
            log.warning("no anonuser found");
            return null;
        }
        //TODO salt
        String doubleHashedPassword = PasswordHelper.hashPassword(hashedPassword, "");
        User user = userRepository.findByDoubleHashedPassword(doubleHashedPassword);

        if (user == null) {
            log.warning("no user found");
            return null;
        }

        anonUser.setUser(user);
        user.setAnonUser(anonUser);
        String token = TokenHelper.generateToken();

        loggedInUsers.add(new Triplet<>(token, user, anonUser));

        System.out.println("trying to authenticate user with username: " + anonName + " and password: " + doubleHashedPassword);
        return user;
    }
}
