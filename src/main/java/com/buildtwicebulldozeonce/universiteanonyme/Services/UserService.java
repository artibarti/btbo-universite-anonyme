package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Helpers.PasswordHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Helpers.TokenHelper;
import com.buildtwicebulldozeonce.universiteanonyme.Models.*;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.*;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Log
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AnonUserRepository anonUserRepository;
    private final CourseRepository courseRepository;
    private  final RatingRepository ratingRepository;
    private List<Triplet<String, User, AnonUser>> loggedInUsers = new ArrayList<Triplet<String, User, AnonUser>>();

    @Autowired
    public UserService(UserRepository userRepository, AnonUserRepository anonUserRepository,
                       CourseRepository courseRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.anonUserRepository = anonUserRepository;
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(@NonNull User user) {
        userRepository.save(user);
    }

    public void addAnonUser(@NonNull AnonUser anonUser) {
        anonUserRepository.save(anonUser);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public Set<Course> getCoursesAdminedByUser(int id) {
        return courseRepository.getCoursesAdminedByUser(id);
    }

    public void updateUser(User user)
    {
        if (userRepository.existsById(user.getId()))
            userRepository.save(user);
    }

    public boolean isUserNameAlreadyTaken(String userName)
    {
        return anonUserRepository.findByAnonName(userName) != null;
    }

    public User authenticateUser(String anonName, String password)
    {
        log.info("authenticate user with email: " + anonName + " and password: " + password);

        AnonUser anonUser = anonUserRepository.findByAnonName(anonName);


        if (anonUser == null || !PasswordHelper.comparePassword(anonUser.getHashedPassword(), password)) {
            log.warning("no anonuser found");
            log.warning(anonUser.getHashedPassword());
            log.warning(password);
            return null;
        }
        //TODO salt
        String doubleHashedPassword = PasswordHelper.hashPassword(anonUser.getHashedPassword(), password);
        User user = userRepository.findByDoubleHashedPassword(doubleHashedPassword);

        if (user == null) {
            log.warning("no user found with :" + doubleHashedPassword +" doubleHashedPassword");
            return null;
        }

        anonUser.setUser(user);
        user.setAnonUser(anonUser);
        String token = TokenHelper.generateToken();

        loggedInUsers.add(new Triplet<>(token, user, anonUser));

        log.info("trying to authenticate user with username: " + anonName + " and password: " + doubleHashedPassword);
        return user;
    }

    public boolean checkIfEmailIsUsed(String email)
    {
        return userRepository.findByEmail(email) != null;
    }

    public Set<Course> getSubscribtionsForUser(int id)
    {
        return this.courseRepository.getSubscribtionsForUser(id);
    }

}
